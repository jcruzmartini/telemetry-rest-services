package com.techner.tau.services.service;

import java.util.List;

import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.AlertEvent;
import com.techner.tau.common.entity.AlertRule;
import com.techner.tau.common.entity.AlertsEventResult;
import com.techner.tau.common.entity.AlertsRuleResult;
import com.techner.tau.common.entity.Notification;
import com.techner.tau.services.mapper.AlertMapper;

public class AlertService {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(AlertService.class);
	/** mapper */
	private final AlertMapper mapper;
	/** factory */
	private final AlertsEventResult.Factory factory;
	/** factory */
	private final AlertsRuleResult.Factory factoryRule;

	/**
	 * @param mapper
	 */
	@Inject
	public AlertService(AlertMapper mapper, AlertsEventResult.Factory factory, AlertsRuleResult.Factory factoryRule) {
		this.mapper = mapper;
		this.factory = factory;
		this.factoryRule = factoryRule;
	}

	/**
	 * Encuentra los eventos de alertas activas de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de alertas activas
	 */
	public AlertsEventResult getActiveAlertEvents(Integer idStation) {
		List<AlertEvent> alerts = mapper.getAlertsEvent(idStation, Notification.Type.ACTIVE.toString());
		AlertsEventResult result = factory.create(alerts);
		return result;
	}

	/**
	 * Encuentra los eventos de alertas diarios de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de alertas del dia
	 */
	public AlertsEventResult getTodayAlertEvents(Integer idStation) {
		List<AlertEvent> alerts = mapper.getAlertsEvent(idStation, Notification.Type.TODAY.toString());
		AlertsEventResult result = factory.create(alerts);
		return result;
	}

	/**
	 * Encuentra todas los eventos de alertas de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de alertas
	 */
	public AlertsEventResult getAllAlertEvents(Integer idStation) {
		List<AlertEvent> alerts = mapper.getAlertsEvent(idStation, Notification.Type.ALL.toString());
		AlertsEventResult result = factory.create(alerts);
		return result;
	}

	/**
	 * Encuentra todas las alertas activas de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de alertas
	 */
	public AlertsRuleResult getAllRuleAlerts(Integer idStation) {
		List<AlertRule> alerts = mapper.getAllAlertRules(idStation);
		AlertsRuleResult result = factoryRule.create(alerts);
		return result;
	}

	/**
	 * Inserta una nueva regla de alerta
	 * 
	 * @param rule
	 *            regla
	 * @param idSation
	 *            id de la estacion
	 */
	@Transactional
	public void insertRule(AlertRule rule, Integer idSation) {
		mapper.insertRule(idSation, rule);
	}

	/**
	 * Inserta una nueva regla de alerta
	 * 
	 * @param rule
	 *            regla
	 * @param idSation
	 *            id de la estacion
	 */
	@Transactional
	public void updateRule(AlertRule rule, Integer idSation) {
		mapper.updateRule(idSation, rule);
	}
}
