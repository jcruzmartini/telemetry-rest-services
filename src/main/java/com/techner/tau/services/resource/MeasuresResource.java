package com.techner.tau.services.resource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.ActualMeasuresResult;
import com.techner.tau.common.entity.Measure;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.exception.ServiceException;
import com.techner.tau.services.common.DateParam;
import com.techner.tau.services.common.ListString;
import com.techner.tau.services.service.MeasuresService;
import com.techner.tau.services.validator.Validator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/measures")
@Api(value = "/measures", description = "Operaciones relacionadas con medidas obtenidas por las estaciones")
public class MeasuresResource {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(MeasuresResource.class);
	/** services de medidas */
	private final MeasuresService service;
	/** factory */
	private final ServiceResult.Factory<ActualMeasuresResult> factory;

	/**
	 * @param service
	 */
	@Inject
	public MeasuresResource(MeasuresService service, ServiceResult.Factory<ActualMeasuresResult> factory) {
		this.service = service;
		this.factory = factory;
	}

	@GET
	@Path("/{id}/actual")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene las medidas ACTUALES", responseClass = "com.techner.tau.common.entity.ActualMeasuresResult")
	@ApiErrors(value = {@ApiError(code = 500, reason = "Error obtiendo medidas actuales")})
	public ServiceResult<ActualMeasuresResult> getActual(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<ActualMeasuresResult> result = null;
		logger.info("Obteniendo medidas actuales para la estacion {}", idSation);

		ActualMeasuresResult measures = service.getActualMeasures(idSation, true);

		if (measures != null) {
			result = factory.create(true, measures);
		} else {
			throw new ServiceException("Error obteniendo la información de las medidas");
		}
		return result;
	}

	@GET
	@Path("/{id}/summary")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene resumen DIARIO, MENSUAL y ANUAL ", responseClass = "com.techner.tau.common.entity.ActualMeasuresResult")
	@ApiErrors(value = {@ApiError(code = 500, reason = "Error obtiendo resumen de medidas")})
	public ServiceResult<ActualMeasuresResult> getSummary(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<ActualMeasuresResult> result = null;
		logger.info("Obteniendo summary de medidas para la estacion {}", idSation);

		ActualMeasuresResult measures = service.getSummaryMeasures(idSation);

		if (measures != null) {
			result = factory.create(true, measures);
		} else {
			throw new ServiceException("Error obteniendo la información de las medidas");
		}
		return result;
	}

	@GET
	@Path("/summary/daily/{id}/{year}/{month}/{day}/{codes}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene resumen DIARIO para un día especificado", responseClass = "com.techner.tau.common.entity.ActualMeasuresResult")
	@ApiErrors(value = {@ApiError(code = 500, reason = "Error obtiendo resumen de medidas"),
			@ApiError(code = 400, reason = "Parámetros obligatorios no presentes")})
	public ServiceResult<ActualMeasuresResult> getDailySummary(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idStation,
			@ApiParam(value = "Día a consultar", required = true) @PathParam("day") Integer day,
			@ApiParam(value = "Mes a consultar", required = true) @PathParam("month") Integer month,
			@ApiParam(value = "Año a consultar", required = true) @PathParam("year") Integer year,
			@ApiParam(value = "Lista de código de variables a consultar, separador [,]", required = false, allowMultiple = true) @PathParam("codes") ListString codes) {
		ServiceResult<ActualMeasuresResult> result = null;
		// Validate
		Validator.notNull(day, month, year);
		Date date = getDay(day, month, year);
		List<String> codeVars = (codes == null) ? null : codes.getList();

		logger.info(String.format("Obteniendo summary DIARIO de medidas con codes %s, estacion [%s] para el día [%s]",
				codeVars, idStation, date));

		ActualMeasuresResult measures = service
				.getSummaryMeasuresByType(idStation, Measure.Type.actual, codeVars, date);
		if (measures != null) {
			result = factory.create(true, measures);
		} else {
			throw new ServiceException("Error obteniendo la información de las medidas para la estacion " + idStation);
		}
		return result;
	}

	@GET
	@Path("/summary/monthly/{id}/{year}/{month}/{codes}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene resumen MENSUAL para un mes especificado", responseClass = "com.techner.tau.common.entity.ActualMeasuresResult")
	@ApiErrors(value = {@ApiError(code = 500, reason = "Error obtiendo resumen de medidas"),
			@ApiError(code = 400, reason = "Parámetros obligatorios no presentes")})
	public ServiceResult<ActualMeasuresResult> getMonthlySummary(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idStation,
			@ApiParam(value = "Mes a consultar", required = true) @PathParam("month") Integer month,
			@ApiParam(value = "Año a consultar", required = true) @PathParam("year") Integer year,
			@ApiParam(value = "Lista de código de variables a consultar, separador [,]", required = false, allowMultiple = true) @PathParam("codes") ListString codes)
			throws ValidationException {
		ServiceResult<ActualMeasuresResult> result = null;
		// Validate
		Validator.notNull(month, year);
		Date date = getDay(null, month, year);
		List<String> codeVars = (codes == null) ? null : codes.getList();

		logger.info(String.format("Obteniendo summary MENSUAL de medidas con codes %s, estacion [%s] para el día [%s]",
				codeVars, idStation, date));

		ActualMeasuresResult measures = service.getSummaryMeasuresByType(idStation, Measure.Type.month, codeVars, date);
		if (measures != null) {
			result = factory.create(true, measures);
		} else {
			throw new ServiceException("Error obteniendo la información de las medidas para la estacion " + idStation);
		}
		return result;
	}

	@GET
	@Path("/{id}/historical")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene las medidas HISTORICAS ", responseClass = "com.techner.tau.common.entity.ActualMeasuresResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetros inválidos"),
			@ApiError(code = 500, reason = "Error obtiendo medidas historicas")})
	public ServiceResult<ActualMeasuresResult> getHistoricalData(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation,
			@ApiParam(value = "Fecha desde", required = true) @QueryParam("initDate") DateParam initDate,
			@ApiParam(value = "Fecha hasta", required = false) @QueryParam("endDate") DateParam endDate,
			@ApiParam(value = "Lista de codigo de variables a consultar, separador [,]", required = false, allowMultiple = true) @QueryParam("codes") ListString codes) {

		// Validate
		Validator.notNull(initDate);
		ServiceResult<ActualMeasuresResult> result = null;
		Date iniDate = initDate.getDate();

		// Opcional
		List<String> list = (codes == null) ? null : codes.getList();
		Date finDate = (endDate == null) ? null : endDate.getDate();

		logger.info(String.format(
				"Obteniendo medidas historicas desde fecha [%s] a fecha hasta [%s]. Estacion %s. Variable %s", iniDate,
				finDate, idSation, codes));

		ActualMeasuresResult measures = service.getHistoricalData(idSation, list, iniDate, finDate);

		if (measures != null) {
			result = factory.create(true, measures);
		} else {
			throw new ServiceException("Error obteniendo la información de las medidas");
		}
		return result;
	}

	/**
	 * Obtiene objeto fecha
	 * 
	 * @param day
	 *            dia
	 * @param month
	 *            mes
	 * @param year
	 *            año
	 * @return objeto fecha
	 */
	private Date getDay(Integer day, Integer month, Integer year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 0);

		if (day != null) {
			cal.set(Calendar.DAY_OF_MONTH, day);
		}
		if (month != null) {
			// Hack: January es 0 para Calendar
			cal.set(Calendar.MONTH, month - 1);
		}
		if (year != null) {
			cal.set(Calendar.YEAR, year);
		}
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return cal.getTime();
	}
}
