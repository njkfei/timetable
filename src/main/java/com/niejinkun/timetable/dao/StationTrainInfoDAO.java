package com.niejinkun.timetable.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.niejinkun.timetable.model.TrainBaseInfo;

public interface StationTrainInfoDAO {
	@Insert("INSERT INTO `train_station_info`(`train_no`, `station_no`) VALUES (#{train_no}, #{station_no})")
	int insert(@Param("train_no")String train_no,@Param("station_no")String station_no);

}
