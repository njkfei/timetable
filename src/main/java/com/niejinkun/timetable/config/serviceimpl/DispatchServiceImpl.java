package com.niejinkun.timetable.config.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.niejinkun.timetable.config.model.ConnctionInfo;
import com.niejinkun.timetable.config.model.RouteInfo;
import com.niejinkun.timetable.config.model.TrainBaseInfo;
import com.niejinkun.timetable.config.model.TrainDetailInfo;
import com.niejinkun.timetable.config.service.DispatchService;
import com.niejinkun.timetable.config.service.TrainTimeTableService;

/**
 * 调度服务
 * 
 * @author sanhao
 *
 */
@Service("dispatchService")
public class DispatchServiceImpl implements DispatchService {
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

	@Value("${train.seed:\"9559\"}")
	private String seed;

	@Autowired
	private OutputStream baseout;

	@Autowired
	private OutputStream detailout;

	@Autowired
	private OutputStream routeout;

	public void dispacth() {
		init();

		ExecutorService pool = Executors.newFixedThreadPool(16);

		for (int i = 0; i < 16; i++) {
			pool.execute(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					while (trainNoQueue.isEmpty() == false) {
						String trainno;
						try {
							trainno = trainNoQueue.take();
							process(trainno);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			});
		}

		//save();
	}

	private void save() {
		System.out.println("trainNoSet : " + trainNoSet.size());
		System.out.println("trainBaseInfoSet : " + trainBaseInfoSet.size());
		System.out.println("trainDetailInfoSet : " + trainDetailInfoSet.size());
		System.out.println("trainRouteInfoSet : " + trainRouteInfoSet.size());

		try {
			for (TrainBaseInfo baseinfo : trainBaseInfoSet) {
				baseout.write(JSON.toJSONString(baseinfo).getBytes());
			}

			for (TrainDetailInfo detail : trainDetailInfoSet) {
				detailout.write(JSON.toJSONString(detail).getBytes());
			}

			for (RouteInfo rotueinfo : trainRouteInfoSet) {
				routeout.write(JSON.toJSONString(rotueinfo).getBytes());
			}
		} catch (

		Exception e)

		{
			e.printStackTrace();
		}
	}

	private void init() {
		process(seed);
	}

	private void process(String trainno) {

		System.out.println(trainno);

		TrainBaseInfo baseinfo = trainTimeTableService.getTrainBaseInfo(trainno);

		System.out.println(baseinfo.toString());

		if (baseinfo != null && baseinfo.getTrain_no() != null) {
			if (trainNoSet.contains(baseinfo.getTrain_no())) {

			} else {
				trainNoSet.add(baseinfo.getTrain_no());

				System.out.println("add train : " + baseinfo.getTrain_no());

				TrainDetailInfo detailinfo = trainTimeTableService.getTrainDetailInfo(baseinfo.getStation_no(),
						baseinfo.getTrain_no());

				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'", Locale.US);

				List<ConnctionInfo> connctioninfos = trainTimeTableService.getTrainConnectionInfo(baseinfo.getStation_no(),
						(sdf.format(new Date()).replaceAll(" ", "%20")));

				List<RouteInfo> routeinfos = trainTimeTableService.getTrainLineInfo(baseinfo.getStation_no(),
						baseinfo.getTrain_no());
				
				trainBaseInfoSet.add(baseinfo);
				System.out.println(baseinfo.toString());

				if(routeinfos != null){
					trainRouteInfoSet.addAll(routeinfos);
				}
				
				try {
					trainNoQueue.put(baseinfo.getTrain_no());

					if (detailinfo != null && detailinfo.getIdOrigine() != null) {
						trainDetailInfoSet.add(detailinfo);
						System.out.println(trainDetailInfoSet.toString());

					}

					if (connctioninfos != null) {
						for (ConnctionInfo item : connctioninfos) {
							if (item != null && item.getNumeroTreno() != null) {
								trainNoQueue.put(item.getNumeroTreno());

								System.out.println(item.toString());
							}
						}
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("put error");
					e.printStackTrace();
				}
			}
		}
	}
}
