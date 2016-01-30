package com.niejinkun.timetable.config.service;


import java.util.List;

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
public interface TrainTimeTableService {
	
	
	public TrainBaseInfo getTrainBaseInfo(String train_no);
	
	/**
	 * 获取列车详细信息，包括正晚点信息
	 * @param station
	 * @param tainno
	 * demo: http://viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/andamentoTreno/S00219/9559
	 * @return
	 */
	public TrainDetailInfo getTrainDetailInfo(String station,String trainno);
	
	/**
	 * 获取列车路径信息
	 * @param station
	 * @param tainno
	 * demo: viaggiatreno.train_detail_info_url=http://viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/tratteCanvas/S00219/9559
	 * @return
	 */
	public List<RouteInfo> getTrainLineInfo(String station,String trainno);
	
	/**
	 * 获取车站的换车信息
	 * @param station
	 * @param tainno
	 * demo: viaggiatreno.train_detail_info_url=http://viaggiatreno.it/viaggiatrenonew/resteasy/viaggiatreno/tratteCanvas/S00219/9559
	 * @return
	 */
	public List<ConnctionInfo> getTrainConnectionInfo(String station,String time);
	
	
}
