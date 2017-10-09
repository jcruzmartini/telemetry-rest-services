package com.techner.tau.services.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.Alert;
import com.techner.tau.common.entity.EntityResult;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.entity.StationInfo;
import com.techner.tau.common.entity.Variable;
import com.techner.tau.common.exception.ServiceException;
import com.techner.tau.services.service.EntityService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/entity")
@Api(value = "/entity", description = "Operaciones relacionadas con entidades")
public class EntityResource {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(EntityResource.class);
	/** service notifications **/
	private final EntityService service;
	/** factory */
	private final ServiceResult.Factory<EntityResult<Variable>> factory;
	/** factory */
	private final ServiceResult.Factory<EntityResult<Alert>> factoryAlert;
	/** factory */
	private final ServiceResult.Factory<EntityResult<StationInfo>> factoryStation;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            station service
	 */
	@Inject
	public EntityResource(EntityService service, ServiceResult.Factory<EntityResult<Variable>> factory,
			ServiceResult.Factory<EntityResult<Alert>> factoryAlert,
			ServiceResult.Factory<EntityResult<StationInfo>> factoryStation) {
		this.service = service;
		this.factory = factory;
		this.factoryAlert = factoryAlert;
		this.factoryStation = factoryStation;
	}

	@GET
	@Path("/{id}/variable/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene la información de las variables habilitadas para una estación", responseClass = "com.techner.tau.services.entity.EntityResult<com.techner.tau.common.entity.Variable>")
	@ApiErrors(value = {@ApiError(code = 500, reason = "Error buscando información de las variables dadas de alta en el sistema")})
	public ServiceResult<EntityResult<Variable>> getAllVariables(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<EntityResult<Variable>> result = null;
		logger.info("Buscando todas las variables activas en el sistema");
		EntityResult<Variable> variables = service.getAllVariables(idSation);
		if (variables != null) {
			result = factory.create(true, variables);
		} else {
			throw new ServiceException("Error obteniendo información de variables");
		}
		return result;
	}

	@GET
	@Path("/alert/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene la información de todas las alertas en el sistema", responseClass = "com.techner.tau.services.entity.EntityResult<com.techner.tau.common.entity.Alert>")
	@ApiErrors(value = {@ApiError(code = 500, reason = "Error buscando información de las variables dadas de alta en el sistema")})
	public ServiceResult<EntityResult<Alert>> getAllAlerts() {
		ServiceResult<EntityResult<Alert>> result = null;
		logger.info("Buscando todas las variables activas en el sistema");
		EntityResult<Alert> alerts = service.getAllAlerts();
		if (alerts != null) {
			result = factoryAlert.create(true, alerts);
		} else {
			throw new ServiceException("Error obteniendo información de las alertas");
		}
		return result;
	}

	@GET
	@Path("/station/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene la información de todas las estaciones en el sistema", responseClass = "com.techner.tau.services.entity.EntityResult<com.techner.tau.common.entity.StationInfo>")
	@ApiErrors(value = {@ApiError(code = 500, reason = "Error buscando información de las estaciones dadas de alta en el sistema")})
	public ServiceResult<EntityResult<StationInfo>> getAllStations() {
		ServiceResult<EntityResult<StationInfo>> result = null;
		logger.info("Buscando todas las estaciones activas en el sistema");
		EntityResult<StationInfo> stations = service.getAllStations();
		if (stations != null) {
			result = factoryStation.create(true, stations);
		} else {
			throw new ServiceException("Error obteniendo información de las estaciones");
		}
		return result;
	}
}
