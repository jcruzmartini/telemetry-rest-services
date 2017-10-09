package com.techner.tau.services.mapper;

import org.apache.ibatis.annotations.Param;

public interface OperationMapper {

	/**
	 * Inserta en tabla SMS
	 * 
	 * @param operation
	 *            operacion a insertar
	 * @return
	 */
	public int insertSMS(@Param("operation") String operation, @Param("force") boolean force);

	/**
	 * Inserta operación
	 * 
	 * @param idStation
	 *            id de la estación
	 * @return
	 */
	public int insertOperation(@Param("idStation") Integer idStation);
}
