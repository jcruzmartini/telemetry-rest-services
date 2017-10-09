package com.techner.tau.services.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.AlertRule;
import com.techner.tau.common.entity.AlertsEventResult;
import com.techner.tau.common.entity.AlertsRuleResult;
import com.techner.tau.common.entity.ServiceRequest;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.exception.ServiceException;
import com.techner.tau.services.service.AlertService;
import com.techner.tau.services.validator.Validator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/alerts")
@Api(value = "/alerts", description = "Operaciones relacionadas con alertas")
public class AlertResource {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(AlertResource.class);
	/** service notifications **/
	private final AlertService service;
	/** factory */
	private final ServiceResult.Factory<AlertsRuleResult> factoryRule;
	/** factory */
	private final ServiceResult.Factory<AlertsEventResult> factoryEvent;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            station service
	 */
	@Inject
	public AlertResource(AlertService service, ServiceResult.Factory<AlertsRuleResult> factory,
			ServiceResult.Factory<AlertsEventResult> factoryEvent) {
		this.service = service;
		this.factoryRule = factory;
		this.factoryEvent = factoryEvent;
	}

	@GET
	@Path("/events/{id}/active")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene los eventos de alertas activas para una estación", responseClass = "com.techner.tau.common.entity.AlertsEventResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
			@ApiError(code = 500, reason = "Error buscando eventos de alertas activas")})
	public ServiceResult<AlertsEventResult> getActiveAlertEvents(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<AlertsEventResult> result = null;
		logger.info("Buscando eventos de alertas activas para la estacion con id  {} del tipo {}", idSation,
				AlertRule.Type.ACTIVE);

		AlertsEventResult alertEvents = service.getActiveAlertEvents(idSation);

		if (alertEvents != null) {
			result = factoryEvent.create(true, alertEvents);
		} else {
			throw new ServiceException("Error buscando eventos de alertas activas");
		}

		logger.info("Eventos de alertas encontradas {}", alertEvents);
		return result;
	}

	@GET
	@Path("/events/{id}/today")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene los eventos de alertas diarias para una estación", responseClass = "com.techner.tau.common.entity.AlertsEventResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
			@ApiError(code = 500, reason = "Error buscando eventos de alertas diarias")})
	public ServiceResult<AlertsEventResult> getTodayAlertEvents(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<AlertsEventResult> result = null;
		logger.info("Buscando eventos de alertas diarias para la estacion con id  {} del tipo {}", idSation,
				AlertRule.Type.TODAY);

		AlertsEventResult alertEvents = service.getTodayAlertEvents(idSation);

		if (alertEvents != null) {
			result = factoryEvent.create(true, alertEvents);
		} else {
			throw new ServiceException("Error buscando eventos de alertas diarias");
		}

		logger.info("Eventos de alertas encontradas {}", alertEvents);
		return result;
	}

	@GET
	@Path("/events/{id}/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene todos los eventos de alertas para una estación", responseClass = "com.techner.tau.common.entity.AlertsEventResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
			@ApiError(code = 500, reason = "Error buscando eventos de alertas")})
	public ServiceResult<AlertsEventResult> getAllAlertEvents(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<AlertsEventResult> result = null;
		logger.info("Buscando todas los eventos de alertas para la estacion con id  {} del tipo {}", idSation,
				AlertRule.Type.ALL);

		AlertsEventResult alertEvents = service.getAllAlertEvents(idSation);

		if (alertEvents != null) {
			result = factoryEvent.create(true, alertEvents);
		} else {
			throw new ServiceException("Error buscando eventos de alertas");
		}

		logger.info("Eventos de alertas encontradas {}", alertEvents);
		return result;
	}

	@GET
	@Path("/rules/{id}/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene todas reglas de alertas para una estación", responseClass = "com.techner.tau.common.entity.AlertsRuleResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
			@ApiError(code = 500, reason = "Error buscando reglas de alertas ")})
	public ServiceResult<AlertsRuleResult> getActiveAlerts(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<AlertsRuleResult> result = null;
		logger.info("Buscando todas las reglas de alertas para la estacion con id  {}", idSation);

		AlertsRuleResult alertEvents = service.getAllRuleAlerts(idSation);

		if (alertEvents != null) {
			result = factoryRule.create(true, alertEvents);
		} else {
			throw new ServiceException("Error buscando reglas de alertas ");
		}

		logger.info("reglas de alertas encontradas {}", alertEvents);
		return result;
	}

	@POST
	@Path("/rules/{id}/new")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Inserta una nueva Regla", responseClass = "com.techner.tau.common.entity.ServiceResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetro obligatorio id vacío")})
	public ServiceResult<AlertsRuleResult> newRule(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation,
			@ApiParam(value = "Información del usuario", required = true) ServiceRequest<AlertRule> request) {

		AlertRule rule = request.getRequest();

		if (rule == null) {
			return factoryRule.create(false, null);
		}
		service.insertRule(rule, idSation);

		return factoryRule.create(true, null);
	}

	@PUT
	@Path("/rules/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Actualiza una regla de alerta", responseClass = "com.techner.tau.common.entity.ServiceResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetro obligatorio id vacío")})
	public ServiceResult<AlertsRuleResult> updateRule(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation,
			@ApiParam(value = "Información del usuario", required = true) ServiceRequest<AlertRule> request) {

		AlertRule rule = request.getRequest();

		if (rule == null) {
			return factoryRule.create(false, null);
		}
		Validator.notNull(rule.getId());

		service.updateRule(rule, idSation);

		return factoryRule.create(true, null);
	}
}
