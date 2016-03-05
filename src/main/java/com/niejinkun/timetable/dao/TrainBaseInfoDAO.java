package com.niejinkun.timetable.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.niejinkun.timetable.model.TrainBaseInfo;

public interface TrainBaseInfoDAO {
	@Insert("INSERT INTO `train_base_info`( `train_no`, `station`, `station_no`) VALUES (#{train_no},#{station},#{station_no})")
	int insert(TrainBaseInfo info);
	
	@Select("SELECT `id`, `train_no`, `station`, `station_no` FROM `train_base_info` WHERE `train_no`=#{train_no}")
	TrainBaseInfo get(@Param("train_no")String train_no);
}
