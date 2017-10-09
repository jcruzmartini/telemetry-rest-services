package com.techner.tau.services.service;

import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.DailyReport;
import com.techner.tau.services.mapper.ReportMapper;

public class ReportService {

	/** Mapper for Reports */
	private final ReportMapper reportMapper;
	/** slf4j logger */
	private static Logger logger = LoggerFactory.getLogger(ReportService.class);

	/**
	 * @param userMapper
	 */
	@Inject
	public ReportService(ReportMapper reportMapper) {
		this.reportMapper = reportMapper;
	}

	/**
	 * Actualiza o Crea información del reporte
	 * 
	 * @param report
	 *            reporte
	 * @return id de reporte, o -1 en caso de error
	 */
	@Transactional
	public int insertOrUpdateDailyReport(Integer idStation, DailyReport report) {
		int id = -1;
		if (report.getId() != null) {
			id = reportMapper.updateDailyReport(idStation, report);
		} else {
			id = reportMapper.insertDailyReport(idStation, report);
		}
		return id;
	}

	/**
	 * Obtiene información del reporte diario para una estacion
	 * 
	 * @param idSation
	 *            id de la estacion
	 * @return reporte diario info
	 */
	public DailyReport getDailyReportInfo(Integer idSation) {
		return reportMapper.getDailyReportInfo(idSation);
	}
}
