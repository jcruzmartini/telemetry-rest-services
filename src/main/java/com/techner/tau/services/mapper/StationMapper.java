package com.techner.tau.services.mapper;

import com.techner.tau.common.entity.StationInfo;

public interface StationMapper {

    /**
     * Obtiene informacion de la estacion
     * 
     * @param idStation
     *            id de la estacion
     * @return info gral de la estacion
     */
    StationInfo getStationInfo(Integer idStation);

    /**
     * Valida si una estacion existe y esta habilitada
     * 
     * @param idStation
     *            id de la estacion
     * @return true si es valida
     */
    int isValidStation(Integer idStation);

    /**
     * Actualiza la información de la estación
     * 
     * @param station
     *            información de la estación
     */
    void update(StationInfo station);

}
