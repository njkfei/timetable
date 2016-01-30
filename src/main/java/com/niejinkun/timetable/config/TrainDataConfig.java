package com.niejinkun.timetable.config;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.niejinkun.timetable.config.model.RouteInfo;
import com.niejinkun.timetable.config.model.TrainBaseInfo;
import com.niejinkun.timetable.config.model.TrainDetailInfo;

/**
 * 列车数据实体
 * @author sanhao
 *
 */
@Configuration
public class TrainDataConfig 
{
	
  @Bean
  public CopyOnWriteArraySet<String> trainNoSet(){
	 CopyOnWriteArraySet<String> trainNoSet = new CopyOnWriteArraySet<String>();
	 return trainNoSet;
  }
  
  @Bean
  public LinkedBlockingQueue<String> trainNoQueue(){
	  LinkedBlockingQueue<String> trainNoQueue = new LinkedBlockingQueue<String>();
		 return trainNoQueue;
  }

  @Bean CopyOnWriteArraySet<TrainBaseInfo> trainBaseInfoSet(){
	  CopyOnWriteArraySet<TrainBaseInfo> trainBaseInfoSet = new CopyOnWriteArraySet<TrainBaseInfo>();
	  return trainBaseInfoSet;
  }
  
  
  @Bean CopyOnWriteArraySet<TrainDetailInfo> trainDetailInfoSet(){
	  CopyOnWriteArraySet<TrainDetailInfo> trainDetailInfoSet = new CopyOnWriteArraySet<TrainDetailInfo>();
	  return trainDetailInfoSet;
  }
  
  @Bean CopyOnWriteArraySet<RouteInfo> trainRouteInfoSet(){
	  CopyOnWriteArraySet<RouteInfo> trainRouteInfoSet = new CopyOnWriteArraySet<RouteInfo>();
	  return trainRouteInfoSet;
  }
  
}
