package com.techner.tau.services.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techner.tau.common.entity.GraphData;

public interface GraphicsMapper {

    /**
     * Obtiene informacion de la estacion
     * 
     * @param idStation
     *            id de la estacion
     * 
     * @param exIds
     *            variables a excluir
     * 
     * @param init
     *            fecha de inicio
     * @param end
     *            fecha de fin
     * @return listado de varibales con su evolución histórica
     */
    List<GraphData> getEvolutionData(@Param("idStation") Integer idStation, @Param("ids") String[] exIds,
            @Param("initDate") Date init, @Param("endDate") Date end);

}
