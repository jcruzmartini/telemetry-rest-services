package com.techner.tau.services.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.techner.tau.common.entity.ActualMeasuresResult;
import com.techner.tau.common.entity.MonitoringInfo;
import com.techner.tau.common.entity.MonitoringResult;
import com.techner.tau.common.entity.StationInfo;
import com.techner.tau.services.mapper.EntityMapper;
import com.techner.tau.services.mapper.StationMapper;

public class StationService {

    /** station mapper */
    private final StationMapper mapper;
    /** entity mapper */
    private final EntityMapper mapperEntity;
    /** factory */
    private final MonitoringInfo.Factory factory;
    /** factory */
    private final MonitoringResult.Factory factoryResult;
    /** measure service */
    private final MeasuresService serviceMeasure;

    /**
     * @param mapper
     */
    @Inject
    public StationService(StationMapper mapper, EntityMapper mapperEntity, MonitoringInfo.Factory factory,
            MeasuresService serviceMeasure, MonitoringResult.Factory factoryResult) {
        this.mapper = mapper;
        this.mapperEntity = mapperEntity;
        this.factory = factory;
        this.serviceMeasure = serviceMeasure;
        this.factoryResult = factoryResult;
    }

    /**
     * Obtiene la info de una estación
     * 
     * @param id
     *            id de la estación
     * @return info
     */
    public StationInfo getStationInfo(Integer id) {
        StationInfo info = mapper.getStationInfo(id);
        return info;
    }

    /**
     * Checkea si es una estacion existente y activa
     * 
     * @param id
     *            id de la estación
     * @return true si esta activa, false en otro caso
     */
    public Boolean isValidStation(Integer id) {
        int exist = mapper.isValidStation(id);
        return (exist == 1) ? true : false;
    }

    /**
     * Obtiene la info de monitoreo de las estaciones
     * 
     * @return información de monitoreo
     */
    public MonitoringResult getMonitoringInfo() {
        List<MonitoringInfo> monitoring = new ArrayList<MonitoringInfo>();
        List<StationInfo> stations = mapperEntity.getAllStations();
        if (null != stations && !stations.isEmpty()) {
            for (StationInfo stationInfo : stations) {
                Integer stationId = stationInfo.getId();
                ActualMeasuresResult actual = serviceMeasure.getActualMeasures(stationId, Boolean.TRUE);
                if (actual != null) {
                    monitoring.add(factory.create(actual, stationInfo));
                }
            }
        }
        return factoryResult.create(monitoring);
    }

    @Transactional
    public void update(StationInfo station) {
        mapper.update(station);
    }
}
