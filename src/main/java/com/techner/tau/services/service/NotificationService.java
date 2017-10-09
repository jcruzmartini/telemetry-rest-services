package com.techner.tau.services.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.Notification;
import com.techner.tau.common.entity.NotificationsResult;
import com.techner.tau.services.mapper.NotificationMapper;

public class NotificationService {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
	/** mapper */
	private final NotificationMapper mapper;
	/** factory */
	private final NotificationsResult.Factory factory;

	/**
	 * @param mapper
	 */
	@Inject
	public NotificationService(NotificationMapper mapper, NotificationsResult.Factory factory) {
		this.mapper = mapper;
		this.factory = factory;
	}

	/**
	 * Encuentra las notificaciones activas de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de notificaciones activas
	 */
	public NotificationsResult getActiveNotifications(Integer idStation) {
		List<Notification> notifications = mapper.getNotifications(idStation, Notification.Type.ACTIVE.toString());
		NotificationsResult result = factory.create(notifications);
		return result;
	}

	/**
	 * Encuentra las notificaciones activas de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de notificaciones activas
	 */
	public NotificationsResult getTodayNotifications(Integer idStation) {
		List<Notification> notifications = mapper.getNotifications(idStation, Notification.Type.TODAY.toString());
		NotificationsResult result = factory.create(notifications);
		return result;
	}

	/**
	 * Encuentra todas las notificaciones de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de notificaciones
	 */
	public NotificationsResult getAllNotifications(Integer idStation) {
		List<Notification> notifications = mapper.getNotifications(idStation, Notification.Type.ALL.toString());
		NotificationsResult result = factory.create(notifications);
		return result;
	}
}
