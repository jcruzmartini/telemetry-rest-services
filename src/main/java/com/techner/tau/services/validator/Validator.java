package com.techner.tau.services.validator;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.techner.tau.common.exception.ValidationException;
import com.techner.tau.services.common.ListQueryParam;

public class Validator {

	/**
	 * Valida que algunos de los argumentos no sea nulo
	 * 
	 * @param parameters
	 *            parametros a validar
	 * 
	 */
	public static final void notNull(Object... parameters) {
		for (Object object : parameters) {
			if (object == null) {
				throw new ValidationException("Parametros obligatorios no pueden ser nulos");
			}
		}
	}

	/**
	 * Valida que una lista no sea nula y no este vacía
	 * 
	 * @param parameters
	 *            parametros a validar
	 * 
	 */
	public static final void notEmptyListQueryParam(ListQueryParam... parameters) {
		for (ListQueryParam col : parameters) {
			if (col == null || col.getList().isEmpty()) {
				throw new ValidationException("Parametros obligatorios no pueden ser nulos o vacíos");
			}
		}
	}

	/**
	 * Valida que una collection no sea nula y no este vacía
	 * 
	 * @param parameters
	 *            parametros a validar
	 * 
	 */
	public static final void notEmptyCollection(Collection... parameters) {
		for (Collection col : parameters) {
			if (col == null || col.isEmpty()) {
				throw new ValidationException("Parametros obligatorios no pueden ser nulos o vacíos");
			}
		}
	}

	/**
	 * Valida que algunos de los cadenas no sean vacíos
	 * 
	 * @param parameters
	 *            parametros a validar
	 * 
	 */
	public static final void notEmpty(String... parameters) {
		for (String str : parameters) {
			if (StringUtils.isEmpty(str)) {
				throw new ValidationException("Parametros obligatorios no pueden ser vacíos");
			}
		}
	}
}
