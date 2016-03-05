package com.niejinkun.timetable.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.niejinkun.timetable.model.RouteInfo;
import com.niejinkun.timetable.model.TrainBaseInfo;
import com.niejinkun.timetable.model.TrainDetailInfo;

@Configuration
@ComponentScan(basePackages ={  "com.niejinkun.timetable.serviceimpl"})
@PropertySource(value = { "classpath:conf/viaggiatreno.properties" })
public class ViaggiatrenoConfig 
{
	
	@Value("${viaggiatreno.resturl}")
	private String baserul;
	
	@Value("${viaggiatreno.starturl}")
	private String starturl;
	// 必须要有这一行，否则上面的＠VALUE无法注入
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	@Bean
	public CopyOnWriteArraySet<String> trainNoSet(){
		return new CopyOnWriteArraySet<String>();
	}
	
	@Bean
	public LinkedBlockingQueue<String> trainNoQueue(){
		return new LinkedBlockingQueue<String>();
	}
	
	@Bean
	public CopyOnWriteArraySet<TrainBaseInfo> trainBaseInfoSet(){
		return  new CopyOnWriteArraySet<TrainBaseInfo>();
	}
	
	@Bean
	public CopyOnWriteArraySet<TrainDetailInfo> trainDetailInfoSet(){
		return new CopyOnWriteArraySet<TrainDetailInfo>();
	}
	
	@Bean
	public ConcurrentHashMap<String,List<RouteInfo>> trainRouteInfoSet(){
		return new ConcurrentHashMap<String,List<RouteInfo>>();
	}
	
	@Bean
	public ConcurrentHashMap<String,Integer> trainOk(){
		return new ConcurrentHashMap<String,Integer>();
	}
	
	@Bean
	public File fileTrainBase(){
		return new File("trainbaseinfo.txt");
	}
	

	
	@Bean
	public File fileTrainDetail(){
		return new File("traindetailinfo.txt");
	}
	
	@Bean
	public File fileTrainConnection(){
		return new File("routeinfo.txt");
	}
	
	@Bean
	public  OutputStream baseout(){
		try {
			return new FileOutputStream(fileTrainBase(), true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}  
	}
	
	public  OutputStream detailout(){
		try {
			return new FileOutputStream(fileTrainDetail(), true);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}  
	}
	
	public  OutputStream routeout(){
		try {
			return new FileOutputStream(fileTrainConnection(), true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}  
	}
}
