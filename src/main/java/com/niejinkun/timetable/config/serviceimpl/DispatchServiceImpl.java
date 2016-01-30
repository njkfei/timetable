package com.niejinkun.timetable.config.serviceimpl;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.niejinkun.timetable.config.model.RouteInfo;
import com.niejinkun.timetable.config.model.TrainBaseInfo;
import com.niejinkun.timetable.config.model.TrainDetailInfo;
import com.niejinkun.timetable.config.service.DispatchService;
import com.niejinkun.timetable.config.service.TrainTimeTableService;

/**
 * 调度服务
 * @author sanhao
 *
 */
public class DispatchServiceImpl implements  DispatchService{
	@Autowired
	private TrainTimeTableService trainTimeTableService;
	
	@Autowired
	private CopyOnWriteArraySet<String> trainNoSet;
	
	@Autowired
	private LinkedBlockingQueue<String> trainNoQueue;
	
	@Autowired
	private CopyOnWriteArraySet<TrainBaseInfo> trainBaseInfoSet;
	
	@Autowired
	private CopyOnWriteArraySet<TrainDetailInfo> trainDetailInfoSet;
	
	@Autowired
	private CopyOnWriteArraySet<RouteInfo> trainRouteInfoSet;
	
	@Value("${train.seed}")
	private String seed;
	public void dispacth() {
		init();
		
		while(true){
			String trainno = trainNoQueue.poll();
			
			process(trainno);
		}
	}
	
	private void init(){
		process(seed);
	}
	
	
	private void process(String trainno){
		TrainBaseInfo baseinfo = trainTimeTableService.getTrainBaseInfo(trainno);
		
		if(baseinfo != null){
			if(trainNoSet.contains(baseinfo.getTrain_no())){
				
			}else{
				trainNoSet.add(baseinfo.getTrain_no());
				trainBaseInfoSet.add(baseinfo);
				try {
					trainNoQueue.put(baseinfo.getTrain_no());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("put error");
					e.printStackTrace();
				}
			}
		}
	}
}
