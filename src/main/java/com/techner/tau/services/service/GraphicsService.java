package com.techner.tau.services.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.GraphData;
import com.techner.tau.services.mapper.GraphicsMapper;

public class GraphicsService {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(GraphicsService.class);
    private final GraphicsMapper mapper;

    /**
     * @param mapper
     */
    @Inject
    public GraphicsService(GraphicsMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Obtiene datos de evolución historica
     * 
     * @param id
     *            id de la estacion
     * @param iDate
     *            fecha inicio
     * @param eDate
     *            fecha fin
     * @return lista de datos
     */
    public List<GraphData> getEvolutionData(Integer id, Date iDate, Date eDate) {
        // TODO, make this configurable
        String[] codes = {"MT"};
        List<GraphData> data = mapper.getEvolutionData(id, codes, iDate, eDate);
        return data;
    }
}
