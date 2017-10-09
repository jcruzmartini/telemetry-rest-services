package com.techner.tau.services.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techner.tau.common.entity.AlertEvent;
import com.techner.tau.common.entity.AlertRule;

public interface AlertMapper {

	/**
	 * Obtiene EVENTOS de alertas
	 * 
	 * @param idStation
	 *            id de la estacion
	 * @type TODAY, ACTIVE, ALL
	 * @return conjunto de alertas
	 */
	List<AlertEvent> getAlertsEvent(@Param("idStation") Integer idStation, @Param("type") String type);

	/**
	 * Obtiene alertas activas
	 * 
	 * @param idStation
	 *            id de la estacion
	 * @return conjunto de alertas activas
	 */
	List<AlertRule> getAllAlertRules(@Param("idStation") Integer idStation);

	/**
	 * Crea una nueva regla
	 * 
	 * @param rule
	 *            regla de alerta
	 * @param idSation
	 *            id stacion
	 */
	void insertRule(@Param("idStation") Integer idSation, @Param("rule") AlertRule rule);

	/**
	 * Actualiza una nueva regla
	 * 
	 * @param rule
	 *            regla de alerta
	 * @param idSation
	 *            id stacion
	 */
	void updateRule(@Param("idStation") Integer idSation, @Param("rule") AlertRule rule);

}
