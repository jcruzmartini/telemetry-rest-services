package com.techner.tau.services.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techner.tau.common.entity.Notification;

public interface NotificationMapper {

	/**
	 * Obtiene notificaciones diarias
	 * 
	 * @param idStation
	 *            id de la estacion
	 * @return conjunto de notificaciones diarias
	 */
	List<Notification> getNotifications(@Param("idStation") Integer idStation, @Param("type") String type);

}
