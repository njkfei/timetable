package com.niejinkun.timetable.serviceimpl;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.niejinkun.timetable.model.ConnctionInfo;
import com.niejinkun.timetable.model.RouteInfo;
import com.niejinkun.timetable.model.TrainBaseInfo;
import com.niejinkun.timetable.model.TrainDetailInfo;
import com.niejinkun.timetable.service.TrainTimeTableService;

import ch.qos.logback.classic.Logger;

/**
 * 情感api
 * 
 * @author sanhao
 *
 */
@Service("trainTimeTableService")
public class TrainTimeTableServiceImpl implements TrainTimeTableService{
	private static Logger logger =  (Logger) LoggerFactory.getLogger(TrainTimeTableServiceImpl.class);

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
					System.out.println("Response content length: " + entity.getContentLength());
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
					System.out.println("Response content length: " + entity.getContentLength());
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
	 * response demo : [{"last":false,"stazioneCorrente":true,"id":"S00219","stazione":"TORINO P.NUOVA","fermata":{"orientamento":"A","kcNumTreno":null,"stazione":"TORINO P.NUOVA","id":"S00219","listaCorrispondenze":null,"programmata":1457189400000,"programmataZero":null,"effettiva":null,"ritardo":0,"partenzaTeoricaZero":null,"arrivoTeoricoZero":null,"partenza_teorica":1457189400000,"arrivo_teorico":null,"isNextChanged":false,"partenzaReale":null,"arrivoReale":null,"ritardoPartenza":0,"ritardoArrivo":0,"progressivo":1,"binarioEffettivoArrivoCodice":null,"binarioEffettivoArrivoTipo":null,"binarioEffettivoArrivoDescrizione":null,"binarioProgrammatoArrivoCodice":null,"binarioProgrammatoArrivoDescrizione":null,"binarioEffettivoPartenzaCodice":null,"binarioEffettivoPartenzaTipo":null,"binarioEffettivoPartenzaDescrizione":null,"binarioProgrammatoPartenzaCodice":null,"binarioProgrammatoPartenzaDescrizione":"16             ","tipoFermata":"P","visualizzaPrevista":true,"nextChanged":false,"nextTrattaType":2,"actualFermataType":0},"partenzaReale":false,"arrivoReale":true,"first":true,"orientamento":["Executive in coda","Executive at the rear","Executive Zugende","Executive en queue","Executive al final del tren","Executive la coada trenului","èé¢ã®Executive","Executiveå¨åå èè½¦å¢","Executive Ð² ÑÐ²Ð¾ÑÑÐµ Ð¿Ð¾ÐµÐ·Ð´Ð°"],"nextTrattaType":0,"actualFermataType":0,"previousTrattaType":null,"trattaType":2},{"last":false,"stazioneCorrente":false,"id":"S00035","stazione":"TORINO PORTA SUSA","fermata":{"orientamento":"A","kcNumTreno":null,"stazione":"TORINO PORTA SUSA","id":"S00035","listaCorrispondenze":null,"programmata":1457189880000,"programmataZero":null,"effettiva":null,"ritardo":0,"partenzaTeoricaZero":null,"arrivoTeoricoZero":null,"partenza_teorica":1457190000000,"arrivo_teorico":1457189880000,"isNextChanged":false,"partenzaReale":null,"arrivoReale":null,"ritardoPartenza":0,"ritardoArrivo":0,"progressivo":6,"binarioEffettivoArrivoCodice":null,"binarioEffettivoArrivoTipo":null,"binarioEffettivoArrivoDescrizione":null,"binarioProgrammatoArrivoCodice":null,"binarioProgrammatoArrivoDescrizione":"2              ","binarioEffettivoPartenzaCodice":null,"binarioEffettivoPartenzaTipo":null,"binarioEffettivoPartenzaDescrizione":null,"binarioProgrammatoPartenzaCodice":null,"binarioProgrammatoPartenzaDescrizione":"2              ","tipoFermata":"F","visualizzaPrevista":true,"nextChanged":false,"nextTrattaType":2,"actualFermataType":0},"partenzaReale":false,"arrivoReale":false,"first":false,"orientamento":["Executive in coda","Executive at the rear","Executive Zugende","Executive en queue","Executive al final del tren","Executive la coada trenului","èé¢ã®Executive","Executiveå¨åå èè½¦å¢","Executive Ð² ÑÐ²Ð¾ÑÑÐµ Ð¿Ð¾ÐµÐ·Ð´Ð°"],"nextTrattaType":0,"actualFermataType":0,"previousTrattaType":0,"trattaType":2},{"last":false,"stazioneCorrente":false,"id":"S01700","stazione":"MILANO CENTRALE","fermata":{"orientamento":"A","kcNumTreno":null,"stazione":"MILANO CENTRALE","id":"S01700","listaCorrispondenze":null,"programmata":1457193000000,"programmataZero":null,"effettiva":null,"ritardo":0,"partenzaTeoricaZero":null,"arrivoTeoricoZero":null,"partenza_teorica":1457193600000,"arrivo_teorico":1457193000000,"isNextChanged":false,"partenzaReale":null,"arrivoReale":null,"ritardoPartenza":0,"ritardoArrivo":0,"progressivo":20,"binarioEffettivoArrivoCodice":null,"binarioEffettivoArrivoTipo":null,"binarioEffettivoArrivoDescrizione":null,"binarioProgrammatoArrivoCodice":null,"binarioProgrammatoArrivoDescrizione":"9              ","binarioEffettivoPartenzaCodice":null,"binarioEffettivoPartenzaTipo":null,"binarioEffettivoPartenzaDescrizione":null,"binarioProgrammatoPartenzaCodice":null,"binarioProgrammatoPartenzaDescrizione":"9              ","tipoFermata":"F","visualizzaPrevista":true,"nextChanged":false,"nextTrattaType":2,"actualFermataType":0},"partenzaReale":false,"arrivoReale":false,"first":false,"orientamento":["Executive in coda","Executive at the rear","Executive Zugende","Executive en queue","Executive al final del tren","Executive la coada trenului","èé¢ã®Executive","Executiveå¨åå èè½¦å¢","Executive Ð² ÑÐ²Ð¾ÑÑÐµ Ð¿Ð¾ÐµÐ·Ð´Ð°"],"nextTrattaType":0,"actualFermataType":0,"previousTrattaType":0,"trattaType":2},{"last":false,"stazioneCorrente":false,"id":"S01820","stazione":"MILANO ROGOREDO","fermata":{"orientamento":"B","kcNumTreno":null,"stazione":"MILANO ROGOREDO","id":"S01820","listaCorrispondenze":null,"programmata":1457194020000,"programmataZero":null,"effettiva":null,"ritardo":0,"partenzaTeoricaZero":null,"arrivoTeoricoZero":null,"partenza_teorica":1457194140000,"arrivo_teorico":1457194020000,"isNextChanged":false,"partenzaReale":null,"arrivoReale":null,"ritardoPartenza":0,"ritardoArrivo":0,"progressivo":23,"binarioEffettivoArrivoCodice":null,"binarioEffettivoArrivoTipo":null,"binarioEffettivoArrivoDescrizione":null,"binarioProgrammatoArrivoCodice":null,"binarioProgrammatoArrivoDescrizione":"8              ","binarioEffettivoPartenzaCodice":null,"binarioEffettivoPartenzaTipo":null,"binarioEffettivoPartenzaDescrizione":null,"binarioProgrammatoPartenzaCodice":null,"binarioProgrammatoPartenzaDescrizione":"8              ","tipoFermata":"F","visualizzaPrevista":true,"nextChanged":false,"nextTrattaType":2,"actualFermataType":0},"partenzaReale":false,"arrivoReale":false,"first":false,"orientamento":["Executive in testa","Executive in the head","Executive Zugspitze","Executive en t&ecirc;te","Executive al inicio del tren","Executive la &icirc;nceputul trenului","é ­ã®ä¸­ã§Executive","Executiveå¨åå èè½¦å¢","Executive Ð² Ð³Ð¾Ð»Ð¾Ð²Ð½Ð¾Ð¹ ÑÐ°ÑÑÐ¸ Ð¿Ð¾ÐµÐ·Ð´Ð°"],"nextTrattaType":0,"actualFermataType":0,"previousTrattaType":0,"trattaType":2},{"last":false,"stazioneCorrente":false,"id":"S08217","stazione":"ROMA TIBURTINA","fermata":{"orientamento":"B","kcNumTreno":null,"stazione":"ROMA TIBURTINA","id":"S08217","listaCorrispondenze":null,"programmata":1457203680000,"programmataZero":null,"effettiva":null,"ritardo":0,"partenzaTeoricaZero":null,"arrivoTeoricoZero":null,"partenza_teorica":1457203800000,"arrivo_teorico":1457203680000,"isNextChanged":false,"partenzaReale":null,"arrivoReale":null,"ritardoPartenza":0,"ritardoArrivo":0,"progressivo":97,"binarioEffettivoArrivoCodice":null,"binarioEffettivoArrivoTipo":null,"binarioEffettivoArrivoDescrizione":null,"binarioProgrammatoArrivoCodice":null,"binarioProgrammatoArrivoDescrizione":"15             ","binarioEffettivoPartenzaCodice":null,"binarioEffettivoPartenzaTipo":null,"binarioEffettivoPartenzaDescrizione":null,"binarioProgrammatoPartenzaCodice":null,"binarioProgrammatoPartenzaDescrizione":"15             ","tipoFermata":"F","visualizzaPrevista":true,"nextChanged":false,"nextTrattaType":2,"actualFermataType":0},"partenzaReale":false,"arrivoReale":false,"first":false,"orientamento":["Executive in testa","Executive in the head","Executive Zugspitze","Executive en t&ecirc;te","Executive al inicio del tren","Executive la &icirc;nceputul trenului","é ­ã®ä¸­ã§Executive","Executiveå¨åå èè½¦å¢","Executive Ð² Ð³Ð¾Ð»Ð¾Ð²Ð½Ð¾Ð¹ ÑÐ°ÑÑÐ¸ Ð¿Ð¾ÐµÐ·Ð´Ð°"],"nextTrattaType":0,"actualFermataType":0,"previousTrattaType":0,"trattaType":2},{"last":true,"stazioneCorrente":false,"id":"S08409","stazione":"ROMA TERMINI","fermata":{"orientamento":"B","kcNumTreno":null,"stazione":"ROMA TERMINI","id":"S08409","listaCorrispondenze":null,"programmata":1457204280000,"programmataZero":null,"effettiva":null,"ritardo":0,"partenzaTeoricaZero":null,"arrivoTeoricoZero":null,"partenza_teorica":null,"arrivo_teorico":1457204280000,"isNextChanged":false,"partenzaReale":null,"arrivoReale":null,"ritardoPartenza":0,"ritardoArrivo":0,"progressivo":99,"binarioEffettivoArrivoCodice":null,"binarioEffettivoArrivoTipo":null,"binarioEffettivoArrivoDescrizione":null,"binarioProgrammatoArrivoCodice":null,"binarioProgrammatoArrivoDescrizione":"3              ","binarioEffettivoPartenzaCodice":null,"binarioEffettivoPartenzaTipo":null,"binarioEffettivoPartenzaDescrizione":null,"binarioProgrammatoPartenzaCodice":null,"binarioProgrammatoPartenzaDescrizione":null,"tipoFermata":"A","visualizzaPrevista":true,"nextChanged":false,"nextTrattaType":2,"actualFermataType":0},"partenzaReale":false,"arrivoReale":false,"first":false,"orientamento":["Executive in testa","Executive in the head","Executive Zugspitze","Executive en t&ecirc;te","Executive al inicio del tren","Executive la &icirc;nceputul trenului","é ­ã®ä¸­ã§Executive","Executiveå¨åå èè½¦å¢","Executive Ð² Ð³Ð¾Ð»Ð¾Ð²Ð½Ð¾Ð¹ ÑÐ°ÑÑÐ¸ Ð¿Ð¾ÐµÐ·Ð´Ð°"],"nextTrattaType":0,"actualFermataType":0,"previousTrattaType":0,"trattaType":2}]
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
					System.out.println("Response content length: " + entity.getContentLength());
					String content;

					content = EntityUtils.toString(entity);
					
					System.out.println("RouteInfo: " + content);

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

		System.out.println("executing request " + get.getURI());
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
					System.out.println("Response content length: " + entity.getContentLength());
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
