package com.niejinkun.timetable.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;

import com.niejinkun.timetable.model.TrainDetailInfo;

public interface TrainDetailInfoDAO {

	@Insert("INSERT INTO `train_detail_info`( `numero_treno`, `categoria`, `id_origine`, `id_destinazione`, `comp_orario_partenza`, `comp_orario_arrivo`, `origine`, `destinazione`) "
			+ "VALUES (#{numeroTreno},#{categoria},#{idOrigine},#{idDestinazione},#{compOrarioPartenza},#{compOrarioArrivo},#{origine},#{destinazione})")
	 @Results(value = {  
			   @Result(property="numeroTreno",column="numero_treno"),  
			   @Result(property="idOrigine", column="id_origine"),
			   @Result(property="idDestinazione", column="id_destinazione"),
			   @Result(property="compOrarioPartenza", column="comp_orario_partenza"),
			   @Result(property="compOrarioArrivo", column="comp_orario_arrivo" )
			 }) 
	int insert(TrainDetailInfo info);
	
	@Select("SELECT `id`, `numero_treno`, `categoria`, `id_origine`, `id_destinazione`, `comp_orario_partenza`, `comp_orario_arrivo`, `origine`, `destinazione` FROM `train_detail_info` "
			+ "WHERE `numero_treno` = #{numero_treno}")
	 @Results(value = {  
			   @Result(property="numeroTreno",column="numero_treno"),  
			   @Result(property="idOrigine", column="id_origine"),
			   @Result(property="idDestinazione", column="id_destinazione"),
			   @Result(property="compOrarioPartenza", column="comp_orario_partenza"),
			   @Result(property="compOrarioArrivo", column="comp_orario_arrivo")
			 }) 
	TrainDetailInfo get(@Param("numero_treno")String numero_treno);
	
}
