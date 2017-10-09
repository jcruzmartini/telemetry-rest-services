package com.techner.tau.services.mapper;

import org.apache.ibatis.annotations.Param;

import com.techner.tau.common.entity.DailyReport;

public interface ReportMapper {

	/**
	 * Crea informacion sobre reporte diario
	 * 
	 * @param report
	 *            reporte diario info
	 */
	int insertDailyReport(@Param("idStation") Integer idStation, @Param("report") DailyReport report);

	/**
	 * Actualiza informacion sobre reporte diario
	 * 
	 * @param report
	 *            reporte diario info
	 */
	int updateDailyReport(@Param("idStation") Integer idStation, @Param("report") DailyReport report);

	/**
	 * Obtiene info del reporte diario
	 * 
	 * @param idStation
	 *            id de la estacion
	 * @return info
	 */
	DailyReport getDailyReportInfo(@Param("idStation") Integer idStation);
}
