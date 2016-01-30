package com.niejinkun.timetable;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.niejinkun.timetable.config.TrainDataConfig;
import com.niejinkun.timetable.config.ViaggiatrenoConfig;
import com.niejinkun.timetable.config.model.ConnctionInfo;
import com.niejinkun.timetable.config.model.RouteInfo;
import com.niejinkun.timetable.config.model.TrainBaseInfo;
import com.niejinkun.timetable.config.model.TrainDetailInfo;
import com.niejinkun.timetable.config.service.TrainTimeTableService;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = Logger.getLogger(App.class);
	public static void main( String[] args )
    {
    	   AbstractApplicationContext  context = new AnnotationConfigApplicationContext(ViaggiatrenoConfig.class,TrainDataConfig.class);
    	   TrainTimeTableService trainTimeTableService = (TrainTimeTableService) context.getBean("trainTimeTableService");
    	   
    	   TrainBaseInfo baseinfo = trainTimeTableService.getTrainBaseInfo("9559");
    	   
    	   System.out.println(baseinfo.toString());
    	   
    	   TrainDetailInfo detailinfo = trainTimeTableService.getTrainDetailInfo(baseinfo.getStation_no(), baseinfo.getTrain_no());
    	   
    	   System.out.println(detailinfo.toString());
    	   
    	   List<RouteInfo> lineinfo = trainTimeTableService.getTrainLineInfo(baseinfo.getStation_no(), baseinfo.getTrain_no());
    	   
    	   for(RouteInfo info : lineinfo){
    		   System.out.println(info);
    	   }
    	   
   		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'",Locale.US);
		
   		logger.info(sdf.format(new Date()));
    	   
   		List<ConnctionInfo> lines = trainTimeTableService.getTrainConnectionInfo(baseinfo.getStation_no(), (sdf.format(new Date()).replaceAll(" ", "%20")));

   		System.out.println(lines.size());
   		for(ConnctionInfo line : lines){
   			System.out.println(line.toString());
   		}
    	  //System.out.println(lines);
    	   

    	   
    	   
    }
}
