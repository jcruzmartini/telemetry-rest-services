package com.techner.tau.services.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.Alert;
import com.techner.tau.common.entity.EntityResult;
import com.techner.tau.common.entity.StationInfo;
import com.techner.tau.common.entity.Variable;
import com.techner.tau.services.mapper.EntityMapper;

public class EntityService {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(EntityService.class);
	/** mapper */
	private final EntityMapper mapper;
	/** factory */
	private final EntityResult.Factory<Variable> factory;
	/** factory */
	private final EntityResult.Factory<Alert> factoryAlert;
	/** factory */
	private final EntityResult.Factory<StationInfo> factoryStation;

	/**
	 * @param mapper
	 */
	@Inject
	public EntityService(EntityMapper mapper, EntityResult.Factory<Variable> factory,
			EntityResult.Factory<Alert> factoryAlert, EntityResult.Factory<StationInfo> factoryStation) {
		this.mapper = mapper;
		this.factory = factory;
		this.factoryAlert = factoryAlert;
		this.factoryStation = factoryStation;
	}

	/**
	 * Encuentra los eventos de alertas activas de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de alertas activas
	 */
	public EntityResult<Variable> getAllVariables(Integer idStation) {
		List<Variable> variables = mapper.getAllVariables(idStation);
		if (variables == null) {
			return null;
		}
		return factory.create(variables);
	}

	/**
	 * Encuentra los eventos de alertas activas de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de alertas activas
	 */
	public EntityResult<Alert> getAllAlerts() {
		List<Alert> alerts = mapper.getAllAlerts();
		if (alerts == null) {
			return null;
		}
		return factoryAlert.create(alerts);
	}

	/**
	 * Encuentra los eventos de alertas activas de una estacion
	 * 
	 * @param idStation
	 *            estacion id
	 * @return lista de alertas activas
	 */
	public EntityResult<StationInfo> getAllStations() {
		List<StationInfo> stations = mapper.getAllStations();
		if (stations == null) {
			return null;
		}
		return factoryStation.create(stations);
	}

}
