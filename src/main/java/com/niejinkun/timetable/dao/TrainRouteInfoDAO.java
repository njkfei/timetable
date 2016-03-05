package com.niejinkun.timetable.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.niejinkun.timetable.model.RouteInfo;

public interface TrainRouteInfoDAO {
	@Insert("INSERT INTO `train_route_info`( `train_no`, `seq`, `station_id`, `stazione`, `first`, `fermate_programmata`, `fermate_partenza_teorica`, `fermate_arrivo_teorico`, `fermate_tipoFermata`, `fermate_progressivo`)"
			+ " VALUES (#{trainno},#{seq},#{id},#{stazione},#{first},#{programmata},#{partenza_teorica},#{arrivo_teorico},#{tipoFermata},#{progressivo})")
	 @Results(value = {  
			 @Result(property="trainno",column="train_no"),  
			 @Result(property="id",column="station_id"),  
			   @Result(property="orientamento",column="fermate_orientamento"),  
			   @Result(property="programmata", column="fermate_programmata"),
			   @Result(property="partenza_teorica", column="fermate_partenza_teorica"),
			   @Result(property="arrivo_teorico", column="fermate_arrivo_teorico"),
			   @Result(property="tipoFermata", column="fermate_tipoFermata"),
			   @Result(property="progressivo", column="fermate_progressivo")
			 }) 
	int insert(RouteInfo info);
	
	@Select("SELECT `id`, `train_no`, `index`, `station_id`, `stazione`, `first`, `fermate_orientamento`, `fermate_programmata`, `fermate_partenza_teorica`, `fermate_arrivo_teorico`, `fermate_tipoFermata`, `fermate_progressivo` FROM `train_route_info` "
			+ "WHERE `train_no` = #{train_no}")
	 @Results(value = {  
			 @Result(property="trainno",column="train_no"),  
			 @Result(property="id",column="station_id"),  
			   @Result(property="orientamento",column="fermate_orientamento"),  
			   @Result(property="programmata", column="fermate_programmata"),
			   @Result(property="partenza_teorica", column="fermate_partenza_teorica"),
			   @Result(property="arrivo_teorico", column="fermate_arrivo_teorico"),
			   @Result(property="tipoFermata", column="fermate_tipoFermata"),
			   @Result(property="progressivo", column="fermate_progressivo")
			 }) 
	RouteInfo get(@Param("train_no")String train_no);
}
