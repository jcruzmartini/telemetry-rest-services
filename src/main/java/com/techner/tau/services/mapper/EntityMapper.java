package com.techner.tau.services.mapper;

import java.util.List;

import com.techner.tau.common.entity.Alert;
import com.techner.tau.common.entity.StationInfo;
import com.techner.tau.common.entity.Variable;

public interface EntityMapper {

	/**
	 * Obtiene lista de todas las variables habilitadas para una estación dada
	 * 
	 * @param idStation
	 *            id estación
	 * @return lista de variables
	 */
	List<Variable> getAllVariables(Integer idStation);

	/**
	 * Obtiene todas las alertas dadas de alta en el sistema
	 * 
	 * @return lista de alertas
	 */
	List<Alert> getAllAlerts();

	/**
	 * Obtiene lista de todas las estaciones activas
	 * 
	 * @return lista de estaciones
	 */
	List<StationInfo> getAllStations();

}
