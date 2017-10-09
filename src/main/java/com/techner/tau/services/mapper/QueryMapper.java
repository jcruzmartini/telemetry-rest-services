package com.techner.tau.services.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.techner.tau.common.entity.QueryResult;

public interface QueryMapper {

	/**
	 * Calcula queries
	 * 
	 * @param idSation
	 *            id de la estacion
	 * @param type
	 *            tipo de Query
	 * @param param
	 *            parametro auxiliar para calculos
	 * @param initDate
	 *            fecha desde
	 * @param endDate
	 *            fecha hasta
	 * @return query result
	 */
	QueryResult calculateQuery(@Param("idStation") Integer idSation, @Param("type") String type,
			@Param("param") String param, @Param("initDate") Date initDate, @Param("endDate") Date endDate);

}
