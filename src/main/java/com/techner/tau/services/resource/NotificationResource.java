package com.techner.tau.services.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.Notification;
import com.techner.tau.common.entity.NotificationsResult;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.exception.ServiceException;
import com.techner.tau.services.service.NotificationService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/notifications")
@Api(value = "/notifications", description = "Operaciones relacionadas con notificaciones ")
public class NotificationResource {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(NotificationResource.class);
	/** service notifications **/
	private final NotificationService service;
	/** factory */
	private final ServiceResult.Factory<NotificationsResult> factory;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            station service
	 */
	@Inject
	public NotificationResource(NotificationService service, ServiceResult.Factory<NotificationsResult> factory) {
		this.service = service;
		this.factory = factory;
	}

	@GET
	@Path("/{id}/active")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene las notificaciones activas de una estación", responseClass = "com.techner.tau.common.entity.NotificationsResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
			@ApiError(code = 500, reason = "Error obteniendo notificaciones activas")})
	public ServiceResult<NotificationsResult> getActiveNotifications(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<NotificationsResult> result = null;
		logger.info("Buscando notificaciones activas para la estacion con id  {} del tipo {}", idSation,
				Notification.Type.ACTIVE);

		NotificationsResult notifications = service.getActiveNotifications(idSation);

		if (notifications != null) {
			result = factory.create(true, notifications);
		} else {
			throw new ServiceException("Error obteniendo notificaciones activas");
		}

		logger.info("Notificaciones encontradas {}", notifications);
		return result;
	}

	@GET
	@Path("/{id}/today")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene las notificaciones diarias de una estación", responseClass = "com.techner.tau.common.entity.NotificationsResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
			@ApiError(code = 500, reason = "Error obteniendo notificaciones diarias")})
	public ServiceResult<NotificationsResult> getTodayNotifications(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<NotificationsResult> result = null;
		logger.info("Buscando notificaciones diarias para la estacion con id  {} del tipo {}", idSation,
				Notification.Type.TODAY);

		NotificationsResult notifications = service.getTodayNotifications(idSation);

		if (notifications != null) {
			result = factory.create(true, notifications);
		} else {
			throw new ServiceException("Error obteniendo notificaciones diarias");
		}

		logger.info("Notificaciones encontradas {}", notifications);
		return result;
	}

	@GET
	@Path("/{id}/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene todas las notificaciones de una estación", responseClass = "com.techner.tau.common.entity.NotificationsResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
			@ApiError(code = 500, reason = "Error obteniendo notificaciones")})
	public ServiceResult<NotificationsResult> getAllNotifications(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation) {
		ServiceResult<NotificationsResult> result = null;
		logger.info("Buscando todas las notificaciones para la estacion con id  {} del tipo {}", idSation,
				Notification.Type.ALL);

		NotificationsResult notifications = service.getAllNotifications(idSation);

		if (notifications != null) {
			result = factory.create(true, notifications);
		} else {
			throw new ServiceException("Error obteniendo todas las notificaciones");
		}

		logger.info("Notificaciones encontradas {}", notifications);
		return result;
	}
}
