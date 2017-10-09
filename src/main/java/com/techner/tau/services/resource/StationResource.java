package com.techner.tau.services.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.MonitoringResult;
import com.techner.tau.common.entity.ServiceRequest;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.entity.StationInfo;
import com.techner.tau.common.exception.ServiceException;
import com.techner.tau.services.service.StationService;
import com.techner.tau.services.validator.Validator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/stations")
@Api(value = "/stations", description = "Operaciones relacionadas con estaciones ")
public class StationResource {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
	/** service station **/
	private final StationService service;
	/** factory */
	private final ServiceResult.Factory<StationInfo> factory;
	/** factory */
	private final ServiceResult.Factory<MonitoringResult> factoryMonitor;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            station service
	 */
	@Inject
	public StationResource(StationService service, ServiceResult.Factory<StationInfo> factory,
			ServiceResult.Factory<MonitoringResult> factoryMonitor) {
		this.service = service;
		this.factory = factory;
		this.factoryMonitor = factoryMonitor;
	}

	@GET
	@Path("/{id}/info")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene la información de una estación", responseClass = "com.techner.tau.common.entity.StationInfo")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
			@ApiError(code = 500, reason = "Error obteniendo información de la estación")})
	public ServiceResult<StationInfo> getInfo(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<StationInfo> result = null;
		logger.info("Buscando informacion de la estacion con id  {}", idSation);
		StationInfo info = service.getStationInfo(idSation);
		if (info != null) {
			result = factory.create(true, info);
		} else {
			throw new ServiceException("Error obteniendo información de la estación");
		}
		logger.info("Informacion de la estacion con id  {}", info);
		return result;
	}

	@GET
	@Path("/monitoring")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene la información de monitoreo de las estaciones", responseClass = "com.techner.tau.common.entity.StationInfo")
	@ApiErrors(value = {@ApiError(code = 500, reason = "Error obteniendo información de la estación")})
	public ServiceResult<MonitoringResult> getMonitoringInfo() {
		ServiceResult<MonitoringResult> result = null;
		logger.info("Buscando informacion de monitoreo de las estaciones");
		MonitoringResult monitor = service.getMonitoringInfo();
		if (monitor != null) {
			result = factoryMonitor.create(true, monitor);
		} else {
			throw new ServiceException("Error obteniendo información de la estación");
		}
		logger.info("Informacion de monitoreo encontradas para {} estaciones", monitor.getEntries().size());
		return result;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Realiza el update de un usuario", responseClass = "com.techner.tau.common.entity.StationInfo")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetro id de la estación vacío")})
	public ServiceResult<StationInfo> update(
			@ApiParam(value = "Información de la estación", required = true) ServiceRequest<StationInfo> request) {
		StationInfo station = request.getRequest();
		// Validate
		Validator.notNull(station.getId());

		logger.info("Actualizando estación con información  {}", station);
		service.update(station);
		logger.info("Estación actualizada correctamente");
		return factory.create(true, null);
	}
}
