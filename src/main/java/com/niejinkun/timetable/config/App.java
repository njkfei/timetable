package com.niejinkun.timetable.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.niejinkun.timetable.model.ConnctionInfo;
import com.niejinkun.timetable.model.RouteInfo;
import com.niejinkun.timetable.model.TrainBaseInfo;
import com.niejinkun.timetable.model.TrainDetailInfo;
import com.niejinkun.timetable.service.JobService;
import com.niejinkun.timetable.service.TrainTimeTableService;


/**
 * Hello world!
 *
 */
public class App {
	static Logger logger = LoggerFactory.getLogger(App.class);
	private static AbstractApplicationContext context;

	public static void main(String[] args) {
	
		context = new AnnotationConfigApplicationContext(ViaggiatrenoConfig.class,MybatisConfiguration.class/*,LogBackConfig.class*/);
		JobService dispatchService = (JobService) context.getBean("dispatchService");
		
		dispatchService.dojob();

	//	test1();
	}

	public static void test1() {
		
		TrainTimeTableService trainTimeTableService = (TrainTimeTableService) context.getBean("trainTimeTableService");

		TrainBaseInfo baseinfo = trainTimeTableService.getTrainBaseInfo("9559");

		System.out.println(baseinfo.toString());

		TrainDetailInfo detailinfo = trainTimeTableService.getTrainDetailInfo(baseinfo.getStation_no(),
				baseinfo.getTrain_no());

		System.out.println(detailinfo.toString());

		List<RouteInfo> lineinfo = trainTimeTableService.getTrainLineInfo(baseinfo.getStation_no(),
				baseinfo.getTrain_no());

		for (RouteInfo info : lineinfo) {
			System.out.println(info.toString());
		}

		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'", Locale.US);

		System.out.println(sdf.format(new Date()));

		List<ConnctionInfo> lines = trainTimeTableService.getTrainConnectionInfo(baseinfo.getStation_no(),
				(sdf.format(new Date()).replaceAll(" ", "%20")));

		System.out.println("" + lines.size());
		for (ConnctionInfo line : lines) {
			System.out.println(line.toString());
		}
		// System.out.println(lines);
	}

}
