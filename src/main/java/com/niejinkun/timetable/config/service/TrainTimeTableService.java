package com.niejinkun.timetable.config.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.niejinkun.timetable.config.model.ConnctionInfo;
import com.niejinkun.timetable.config.model.RouteInfo;
import com.niejinkun.timetable.config.model.TrainBaseInfo;
import com.niejinkun.timetable.config.model.TrainDetailInfo;

/**
 * 情感api
 * 
 * @author sanhao
 *
 */
@Service
public class TrainTimeTableService {
	private static Logger logger = Logger.getLogger(TrainTimeTableService.class);

	@Value("${viaggiatreno.starturl}")
	private String baseinfo_url;
	
	@Value("${viaggiatreno.train_base_info_url}")
	private String detailinfo_url;

	@Value("${viaggiatreno.train_detail_info_url}")
	private String trainno_line_info;
	
	@Value("${viaggiatreno.train_connections_url}")
	private String station_connection_info;
	
	public TrainBaseInfo getTrainBaseInfo(String train_no) {
		String url = baseinfo_url + train_no;
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();

		HttpGet get = new HttpGet(url);
		get.setHeader("Host", "viaggiatreno.it");
		get.setHeader("Origin", "http://www.trenitalia.com");
		get.setHeader("Referer", "http://viaggiatreno.it/viaggiatrenonew/index.jsp");
		get.setHeader("X-Requested-With","XMLHttpRequest");
		get.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36");

		logger.info("executing request " + get.getURI());

		// 执行get请求.
		HttpResponse response;

		try {
			response = httpClient.execute(get);
			// 获取响应状态
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 打印响应内容长度
					logger.info("Response content length: " + entity.getContentLength());
					String content;

					content = EntityUtils.toString(entity);

					return new TrainBaseInfo(content);
				}
			}else{
				logger.warn("HttpStatus Code : " + statusCode);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 获取列车详细信息，包括正晚点信息
	 * @param station
	 * @param tainno
	 * demo: http://viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/andamentoTreno/S00219/9559
	 * @return
	 */
	public TrainDetailInfo getTrainDetailInfo(String station,String trainno){
		String url = detailinfo_url + station + "/" + trainno;
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();

		HttpGet get = new HttpGet(url);
		get.setHeader("Accept","application/json");
		get.setHeader("Host", "viaggiatreno.it");
		get.setHeader("Origin", "http://www.trenitalia.com");
		get.setHeader("Referer", "http://viaggiatreno.it/viaggiatrenonew/index.jsp");
		get.setHeader("Accept-Language","en");
		get.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36");

		logger.info("executing request " + get.getURI());

		// 执行get请求.
		HttpResponse response;

		try {
			response = httpClient.execute(get);
			// 获取响应状态
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 打印响应内容长度
					logger.info("Response content length: " + entity.getContentLength());
					String content;

					content = EntityUtils.toString(entity);

					return JSON.parseObject(content,TrainDetailInfo.class);
				}
			}else{
				logger.warn("HttpStatus Code : " + statusCode);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	/**
	 * 获取列车路径信息
	 * @param station
	 * @param tainno
	 * demo: viaggiatreno.train_detail_info_url=http://viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/tratteCanvas/S00219/9559
	 * @return
	 */
	public List<RouteInfo> getTrainLineInfo(String station,String trainno){
		String url = trainno_line_info + station + "/" + trainno;
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();

		HttpGet get = new HttpGet(url);
		get.setHeader("Accept","application/json");
		get.setHeader("Host", "viaggiatreno.it");
		get.setHeader("Origin", "http://www.trenitalia.com");
		get.setHeader("Referer", "http://viaggiatreno.it/viaggiatrenonew/index.jsp");
		get.setHeader("Accept-Language","it");
		get.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36");

		logger.info("executing request " + get.getURI());

		// 执行get请求.
		HttpResponse response;

		try {
			response = httpClient.execute(get);
			// 获取响应状态
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 打印响应内容长度
					logger.info("Response content length: " + entity.getContentLength());
					String content;

					content = EntityUtils.toString(entity);

					return JSON.parseArray(content, RouteInfo.class);
				}
			}else{
				logger.warn("HttpStatus Code : " + statusCode);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 获取车站的换车信息
	 * @param station
	 * @param tainno
	 * demo: viaggiatreno.train_detail_info_url=http://viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/tratteCanvas/S00219/9559
	 * @return
	 */
	public List<ConnctionInfo> getTrainConnectionInfo(String station,String time){
		String url = station_connection_info + station + "/" + time;
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();

		HttpGet get = new HttpGet(url);
		get.setHeader("Accept","application/json");
		get.setHeader("Host", "viaggiatreno.it");
		get.setHeader("Origin", "http://www.trenitalia.com");
		get.setHeader("Referer", "http://viaggiatreno.it/viaggiatrenonew/index.jsp");
		get.setHeader("Accept-Language","it");
		get.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36");

		logger.info("executing request " + get.getURI());
		System.out.println("executing request " + get.getURI());
		

		// 执行get请求.
		HttpResponse response;

		try {
			response = httpClient.execute(get);
			// 获取响应状态
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 打印响应内容长度
					logger.info("Response content length: " + entity.getContentLength());
					String content;

					content = EntityUtils.toString(entity);

					return JSON.parseArray(content, ConnctionInfo.class);
				}
			}else{
				logger.warn("HttpStatus Code : " + statusCode);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws java.text.ParseException{
		Date date = new Date();
		
		System.out.println(date.toLocaleString());
		System.out.println(date.toGMTString());
		try {
			System.out.println(URLEncoder.encode(date.toGMTString()).replaceAll(" ", "%20"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String src = "Sun%20Jan%2031%202016%2002:10:00%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)";
		System.out.println(src);
		System.out.println(URLDecoder.decode(src));
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'",Locale.US);
		
		System.out.println(sdf.format(new Date()));
		System.out.println((sdf.format(new Date())).replaceAll(" ", "%20"));
		System.out.println(URLEncoder.encode(sdf.format(new Date())).replaceAll("\\+", "%20"));
		Date date1 = sdf.parse("Sun Jan 31 2016 02:10:00 GMT");
		System.out.println((sdf.format(date1)).replaceAll(" ", "%20"));
		
		 Locale localeEN = Locale.US;
		   Locale localeFrance = Locale.FRANCE;
		    TimeZone timeZoneMiami = TimeZone.getDefault();
		   TimeZone timeZoneParis =   TimeZone.getTimeZone("Europe/Paris");
		   DateFormat dateFormatter = DateFormat.getDateTimeInstance(
		DateFormat.FULL,
		DateFormat.FULL,
		localeEN);
		   DateFormat dateFormatterParis = DateFormat.getDateTimeInstance(
		DateFormat.DATE_FIELD,
		DateFormat.FULL,
		localeFrance);
		   Date curDate = new Date(); //以GMT格式记录当前计算机时间
		   System.out.println("Display for Miami office.");
		   System.out.println(dateFormatterParis.format(curDate));
		   System.out.println(curDate);
	}
}
