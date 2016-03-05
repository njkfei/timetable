package com.niejinkun.timetable.config;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author lanyonm
 */
@Configuration
@PropertySource(value = { "classpath:conf/ms.properties" })
public class ItalyTrainConfig {
	// private static Logger logger = Logger.getLogger(ItalyTrainConfig.class);

	// @Value("${bd.img:http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1453859143334_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E5%A8%81%E5%B0%BC%E6%96%AF+%E9%AB%98%E6%B8%85%E7%B4%A0%E6%9D%90}")
	@Value("https://www.lefrecce.it/B2CWeb/searchExternal.do?parameter=initBaseSearch&lang=it")
	// @Value("${bd.img:http://image.baidu.com/search/index}")
	private String url;

	@Value("http://www.trenord.it/en/timetable/default.aspx?aspxerrorpath=/en/timetable/timetable.aspx")
	private String redirect_url;

	@Value("Milano ( Tutte Le Stazioni )")
	private String departureStation;
	
	@Value("Roma ( Tutte Le Stazioni )")
	private String arrivalStation;
	
	@Value("30-01-2016")
	private String departureDate;
	
	@Value("20")
	private String departureTime;
	
	
	@Value("3")
	private String noOfAdults;
	
	@Value("1")
	private String noOfChildren;
	
	@Value("tutti")
	private String selectedTrainType;
	
	@Value("@null")
	private String selectedTrainClassification;
	
	// 必须要有这一行，否则上面的＠VALUE无法注入
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public CloseableHttpClient httpClientItaly() {
		HttpClientBuilder builder = HttpClientBuilder.create();
		// builder.setEverything(everything); // configure it
		CloseableHttpClient httpClient = builder.build();
		return httpClient;
	}

	@Bean
	public HttpPost httpPostItaly() {
		try {
			URIBuilder builder = new URIBuilder(url);

			// builder.setParameter("faceRectangles", faceRectangles);
			URI uri = builder.build();
			HttpPost post = new HttpPost(uri);
			// post.setHeader("Content-Type", "application/json");

/*			Host:www.lefrecce.it
			Origin:http://www.trenitalia.com
			Referer:http://www.trenitalia.com/
*/				
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setHeader("Host", "www.lefrecce.it");
			post.setHeader("Origin", "http://www.trenitalia.com");
			post.setHeader("Referer", "http://www.trenitalia.com");
			post.setHeader("Upgrade-Insecure-Requests", "1");
			post.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36");

			post.setEntity(params());
			System.out.println(post.toString());

			return post;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * url_desktop:https://www.lefrecce.it/B2CWeb/searchExternal.do?parameter=initBaseSearch&lang=it
url_mobile:https://www.lefrecce.it/msite/SearchExternal.do?parameter=initBaseSearch&lang=it
tripType:on
isRoundTrip:false
departureStation:Genova ( Tutte Le Stazioni )
arrivalStation:Roma ( Tutte Le Stazioni )
departureDate:29-01-2016
departureTime:16
noOfAdults:1
noOfChildren:0
selectedTrainType:tutti
selectedTrainClassification:
	 */
	@Bean
	public UrlEncodedFormEntity params() {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("url_desktop:https", "https://www.lefrecce.it/B2CWeb/searchExternal.do?parameter=initBaseSearch&lang=it"));
		nameValuePairs.add(new BasicNameValuePair("url_mobile:https", "https://www.lefrecce.it/msite/SearchExternal.do?parameter=initBaseSearch&lang=it"));
		nameValuePairs.add(new BasicNameValuePair("tripType", "on"));
		nameValuePairs.add(new BasicNameValuePair("isRoundTrip", "false"));
		nameValuePairs.add(new BasicNameValuePair("departureStation",departureStation ));
		nameValuePairs.add(new BasicNameValuePair("arrivalStation", arrivalStation));		
		nameValuePairs.add(new BasicNameValuePair("departureDate",departureDate ));
		nameValuePairs.add(new BasicNameValuePair("departureTime", departureTime));
		nameValuePairs.add(new BasicNameValuePair("noOfAdults", noOfAdults));
		nameValuePairs.add(new BasicNameValuePair("noOfChildren", noOfChildren));
		nameValuePairs.add(new BasicNameValuePair("selectedTrainType", selectedTrainType));
		nameValuePairs.add(new BasicNameValuePair("selectedTrainClassification", selectedTrainClassification));

		UrlEncodedFormEntity entiry;
		try {
			entiry = new UrlEncodedFormEntity(nameValuePairs);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return entiry;
	}

	@Bean
	public HttpGet httpGetItaly() {
		try {

			URIBuilder builder = new URIBuilder(redirect_url);

			URI uri = builder.build();
			HttpGet get = new HttpGet(uri);

			get.setHeader("Content-Type", "application/x-www-form-urlencoded");
			get.setHeader("Host", "www.lefrecce.it");
			get.setHeader("Origin", "http://www.trenitalia.com");
			get.setHeader("Referer", "http://www.trenitalia.com");
			get.setHeader("Upgrade-Insecure-Requests", "1");
			get.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36");

			
			System.out.println(get.toString());

			return get;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 	 cFID=Rxg8RKjaUKQO; JSESSIONID=00007SYf5IpHx0DQpSzUl4oViJJ:18orv2gbo
	@Bean
	public HttpContext localContext() {
		HttpContext localContext = new BasicHttpContext();
		
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore());
		
		return localContext;
	}
	
	@Bean
	public CookieStore cookieStore(){
		CookieStore cookieStore = new BasicCookieStore(); 
		
/*		BasicClientCookie cookie1 = new BasicClientCookie("b2b.apm.cookie", "B2C");
				cookie1.setDomain(".lefrecce.it");
				cookie1.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
				cookie1.setPath("/");
				
				BasicClientCookie cookie2 = new BasicClientCookie("cFID", "Rxg8RKjaUKQO");
				cookie2.setDomain(".lefrecce.it");
				cookie2.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
				cookie2.setPath("/");
				
				BasicClientCookie cookie3 = new BasicClientCookie("JSESSIONID", "00007SYf5IpHx0DQpSzUl4oViJJ:18orv2gbo");
				cookie3.setDomain(".lefrecce.it");
				cookie3.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
				cookie3.setPath("/");

		cookieStore.addCookie(cookie1); 
		cookieStore.addCookie(cookie2); 
		cookieStore.addCookie(cookie3); */
		
		return cookieStore;
	}
}
