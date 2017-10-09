package com.techner.tau.services.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.DailyReport;
import com.techner.tau.common.entity.ServiceRequest;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.services.service.ReportService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/reports")
@Api(value = "/reports", description = "Operaciones relacionadas con reportes ")
public class ReportsResource {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(ReportsResource.class);
	/** servicio de usuario */
	private final ReportService service;
	/** factory */
	private final ServiceResult.Factory<DailyReport> factory;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            servicio
	 */
	@Inject
	public ReportsResource(ReportService service, ServiceResult.Factory<DailyReport> factory) {
		this.service = service;
		this.factory = factory;
	}

	@POST
	@Path("/{id}/daily")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Crea o actualiza reporte diario", responseClass = "com.techner.tau.common.entity.ServiceResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetros obligatorios email o password vacíos")})
	public ServiceResult<DailyReport> createOrUpdateDailyReport(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation,
			@ApiParam(value = "Información del reporte", required = true) ServiceRequest<DailyReport> request) {
		ServiceResult<DailyReport> result = null;
		DailyReport report = request.getRequest();
		logger.info("Creando/Actualizando reporte {} para estacion con id {} ", report, idSation);
		int id = service.insertOrUpdateDailyReport(idSation, report);
		if (id > 0) {
			result = factory.create(true, null);
		} else {
			result = factory.create(false, null);
		}
		return result;
	}

	@GET
	@Path("/{id}/daily")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene info del reporte diario", responseClass = "com.techner.tau.common.entity.DailyReport")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetros obligatorios email o password vacíos")})
	public ServiceResult<DailyReport> getDailyReport(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<DailyReport> result = null;
		logger.info("Obteniendo informacion de reporte diario para estacion con id {} ", idSation);
		DailyReport report = service.getDailyReportInfo(idSation);
		if (report != null) {
			result = factory.create(true, report);
		} else {
			result = factory.create(false, null);
		}
		return result;
	}
}
