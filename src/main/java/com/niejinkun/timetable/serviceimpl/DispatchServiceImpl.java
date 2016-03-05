package com.niejinkun.timetable.serviceimpl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.niejinkun.timetable.dao.StationTrainInfoDAO;
import com.niejinkun.timetable.dao.TrainBaseInfoDAO;
import com.niejinkun.timetable.dao.TrainDetailInfoDAO;
import com.niejinkun.timetable.dao.TrainRouteInfoDAO;
import com.niejinkun.timetable.model.ConnctionInfo;
import com.niejinkun.timetable.model.RouteInfo;
import com.niejinkun.timetable.model.TrainBaseInfo;
import com.niejinkun.timetable.model.TrainDetailInfo;
import com.niejinkun.timetable.service.JobService;
import com.niejinkun.timetable.service.TrainTimeTableService;

/**
 * 调度服务
 * 
 * @author sanhao
 *
 */
@Service("dispatchService")
public class DispatchServiceImpl implements JobService {
	static Logger logger = LoggerFactory.getLogger(DispatchServiceImpl.class);

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
	private ConcurrentHashMap<String, List<RouteInfo>> trainRouteInfoSet;

	@Autowired
	private ConcurrentHashMap<String, Integer> trainOk;
	
	@Autowired
	private ConcurrentHashMap<String,String> stationMap;

	@Value("${train.seed:9559}")
	private String seed_trainno;
	@Value("${station.seed:S00219}")
	private String seed_stationno;

	@Autowired
	private OutputStream baseout;

	@Autowired
	private OutputStream detailout;

	@Autowired
	private OutputStream routeout;

	@Autowired
	private TrainBaseInfoDAO trainBaseInfoDAO;

	@Autowired
	private TrainDetailInfoDAO trainDetailInfoDAO;

	@Autowired
	private StationTrainInfoDAO stationTrainInfoDAO; 
	
	@Autowired
	private TrainRouteInfoDAO trainRouteInfoDAO;
	
	

	@Value("${pool.size:16}")
	private int pool_size;

	private static final String SPLIT = "__";

	public void dojob() {
		init();

		ExecutorService pool = Executors.newFixedThreadPool(pool_size);
		@SuppressWarnings("rawtypes")
		List<Future> fs = new ArrayList<Future>();

		for (int i = 0; i < 16; i++) {
			Future<String> f = pool.submit(new Callable() {

				public String call() throws Exception {
					// TODO Auto-generated method stub
					while (trainNoQueue.isEmpty() == false) {
						String train;
						String trainno;
						String stationno;
						train = trainNoQueue.take();
						trainno = getTrainNo(train);
						stationno = getStationNo(train);
						if (existTrainOk(trainno, stationno) == false) {
							System.out.println("process train : " + train);
							process(trainno, stationno);
						}

					}
					return "ok";
				}
			});

			fs.add(f);
		}

		pool.shutdown();

		for (Future f : fs) {
			try {
				System.out.println(f.get().toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		save();
	}

	private void save() {
		System.out.println("trainNoSet : " + trainNoSet.size());
		System.out.println("trainBaseInfoSet : " + trainBaseInfoSet.size());
		System.out.println("trainDetailInfoSet : " + trainDetailInfoSet.size());
		System.out.println("trainRouteInfoSet : " + trainRouteInfoSet.size());

		try {
			for (TrainBaseInfo baseinfo : trainBaseInfoSet) {
				baseout.write(JSON.toJSONString(baseinfo).getBytes());
				baseout.write("\r\n".getBytes());
			}

			for (TrainDetailInfo detail : trainDetailInfoSet) {
				detailout.write(JSON.toJSONString(detail).getBytes());
				baseout.write("\r\n".getBytes());
			}

			/*
			 * for (String item : trainRouteInfoSet.keySet()) {
			 * routeout.write(JSON.toJSONString(item).getBytes());
			 * routeout.write(JSON.toJSONString(trainRouteInfoSet.get(item).
			 * toArray().getBytes()); }
			 */
		} catch (

		Exception e)

		{
			e.printStackTrace();
		}
	}

	private void init() {
		// trainOk.put(seed, 0);

		process(seed_trainno, seed_stationno);
	}

	@Transactional
	private void process(String trainno, String stationno) {
		onNewTrain(trainno);
		
		if (existTrainOk(trainno, stationno) == true) {
			System.out.println(" repeat train  : " + trainno + SPLIT + stationno);
		} else {
			setTrainOk(trainno, stationno);

			TrainDetailInfo detailinfo = trainTimeTableService.getTrainDetailInfo(stationno, trainno);

			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'", Locale.US);

			List<ConnctionInfo> connctioninfos = trainTimeTableService.getTrainConnectionInfo(stationno,
					(sdf.format(new Date()).replaceAll(" ", "%20")));

			List<RouteInfo> routeinfos = trainTimeTableService.getTrainLineInfo(stationno, trainno);

			if (routeinfos != null) {
				System.out.println("add routeinfos " + routeinfos);

				for (RouteInfo item : routeinfos) {
					if (item != null && item.getId() != null) {

						System.out.println(item.toString());
						if (existTrainOk(trainno, item.getId()) == false) {

							insertTrainOk(trainno, item.getId());
							
							stationMap.put(item.getId(), item.getStazione());
							
							onNewStation(item.getId(), item.getStazione());

							System.out.println(trainno + SPLIT + item.getId());
						}
					}
				}
			} else {
				System.out.println("ERROR routeinfos is empty");
			}

/*			if (detailinfo != null && detailinfo.getIdOrigine() != null) {
			//	trainDetailInfoSet.add(detailinfo);
			//	System.out.println(" add detailinfo " + trainDetailInfoSet.toString());
			}*/

			if (connctioninfos != null) {
				for (ConnctionInfo item : connctioninfos) {
					if (item != null && item.getNumeroTreno() != null) {

						System.out.println(item.toString());
						if (existTrainOk(item.getNumeroTreno(), item.getCodOrigine()) == false) {

							insertTrainOk(item.getNumeroTreno(), item.getCodOrigine());
							
							System.out.println(item.getNumeroTreno() + SPLIT + item.getCodOrigine());
						}

					}
				}
			}

		}
	}

	private void onNewStation(String id, String stazione) {
		if(stationMap.containsKey(id) == false){
			stationMap.put(id, id);
			
			stationTrainInfoDAO.insertStation(id,stazione);
		}
		
	}

	public boolean existTrainOk(String trainno, String stationno) {
		String key = trainno + SPLIT + stationno;

		if (trainOk.containsKey(key) && trainOk.get(key) == 1) {
			return true;
		}

		return false;
	}

	public boolean insertTrainOk(String trainno, String stationno) {
		String key = trainno + SPLIT + stationno;

		if (trainOk.containsKey(key)) {
			return false;
		}

		try {
			trainOk.put(key, 0);
			trainNoQueue.put(key);
			System.out.println("add trainno to queue : " + key);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean setTrainOk(String trainno, String stationno) {
		String key = trainno + SPLIT + stationno;

		trainOk.put(key, 1);
		
		stationTrainInfoDAO.insert(trainno,stationno);

		return true;
	}

	public String getTrainNo(String src) {
		String[] parts = src.split(SPLIT);

		if (parts.length == 2) {
			return parts[0];
		} else {
			return "";
		}

	}

	public String getStationNo(String src) {
		String[] parts = src.split(SPLIT);

		if (parts.length == 2) {
			return parts[1];
		} else {
			return "";
		}
	}

	private void onNewTrain(String trainno) {
		if (trainNoSet.contains(trainno) == false) {
			System.out.println(" getTrainBaseInfo : " + trainno);

			TrainBaseInfo baseinfo = trainTimeTableService.getTrainBaseInfo(trainno);

			if (baseinfo != null) {

				System.out.println("TrainBaseInfo " + baseinfo.toString());
				trainNoSet.add(trainno);
				trainBaseInfoSet.add(baseinfo);
				trainBaseInfoDAO.insert(baseinfo);

				TrainDetailInfo detailinfo = trainTimeTableService.getTrainDetailInfo(baseinfo.getStation_no(),
						trainno);

				if (detailinfo != null) {
					System.out.println(detailinfo.toString());
					trainDetailInfoDAO.insert(detailinfo);
				}

				List<RouteInfo> routeinfos = trainTimeTableService.getTrainLineInfo(baseinfo.getStation_no(), trainno);

				if (routeinfos != null) {
					System.out.println("add routeinfos " + routeinfos);

					int i = 1;
					for (RouteInfo item : routeinfos) {
						if (item != null && item.getId() != null) {

							item.setTrainno(trainno);
							item.setSeq(i++);

							System.out.println(item.toString());
							if (existTrainOk(trainno, item.getId()) == false) {

								insertTrainOk(trainno, item.getId());

								// trainRouteInfoDAO.insert(item);
							}
						}
					}

					// trainRouteInfoSet.put(trainno,routeinfos);
				}
			} else {
				System.out.println("ERRO no BASE TRAIN info : " + trainno);
			}
		}
	}
}
