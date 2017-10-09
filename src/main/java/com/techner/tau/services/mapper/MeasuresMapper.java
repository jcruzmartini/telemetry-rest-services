package com.techner.tau.services.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techner.tau.common.entity.MeasureResult;

public interface MeasuresMapper {

	/**
	 * Obtiene el acumulado de la lluvia actual
	 * 
	 * @param idStation
	 *            id de la estacion
	 * @return datos de las variables
	 */
	MeasureResult getAcumVariable(@Param("idStation") Integer idStation, @Param("type") String type,@Param("code") String code );

	/**
	 * Obtiene los id de variables habilitados
	 * 
	 * @param idStation
	 *            id de la estación
	 * @return id de las variables hab.
	 */
	List<String> getEnableVariables(Integer idStation);

	/**
	 * Obtiene datos de las variables segun filtros
	 * 
	 * @param idStation
	 *            id de la estacion
	 * @param ids
	 *            id de las variables a consultar
	 * @param exIds
	 *            id de las variables a excluir
	 * @param type
	 *            tipo de medidas a consultar
	 * @return lista de medidas
	 */
	List<MeasureResult> getMeasures(@Param("idStation") Integer idStation, @Param("varCodes") List<String> codes,
			@Param("exVarCodes") List<String> exCodes, @Param("type") String type);

	/**
	 * Obitene lista de medidas, segun periodo y día/mes/año especificad
	 * 
	 * @param idStation
	 *            id de la estación
	 * @param codes
	 *            codigos de las variables
	 * @param type
	 *            tipo de medidas a consultar
	 * @param Date
	 *            dia/mes/año a consultar
	 * @return lista de medidas
	 */
	List<MeasureResult> getMeasuresByDate(@Param("idStation") Integer idStation, @Param("varCodes") List<String> codes,
			@Param("type") String type, @Param("date") Date date);

	/**
	 * Obtiene cuando fue la ultima vez que actualizo la estacion
	 * 
	 * @param idStation
	 *            id de la estacion
	 * @return minutos de la ult actualizacion
	 */
	Integer getLastUpdate(Integer idStation);

	/**
	 * Obtiene las medidas historicas
	 * 
	 * @param idStation
	 *            id de la estacion
	 * @param idVar
	 *            id de la variable
	 * @param init
	 *            fecha inicio
	 * @param end
	 *            fecha fin
	 * @return medidas
	 */
	List<MeasureResult> getHistoricalData(@Param("idStation") Integer idStation, @Param("varCodes") List<String> idVar,
			@Param("dateIni") Date init, @Param("dateEnd") Date end);
}
