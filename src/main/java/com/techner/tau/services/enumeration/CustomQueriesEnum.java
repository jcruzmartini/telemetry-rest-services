package com.techner.tau.services.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration para almacenar los nombres de las queries especiales
 * 
 * @author juan
 * 
 */
public enum CustomQueriesEnum {

	GRADOS_DIAS("grados-dia"), LLUVIA_ACUM("lluvia-acumulada"), HORAS_LUZ("horas-luz"), HORAS_FRIO("horas-frio"), EVAP(
			"evapotranspiration"), UNKNOWN("unknown");

	String key;

	// Reverse-lookup map for getting a day from an abbreviation
	private static final Map<String, CustomQueriesEnum> lookup = new HashMap<String, CustomQueriesEnum>();

	static {
		for (CustomQueriesEnum d : CustomQueriesEnum.values())
			lookup.put(d.getKey(), d);
	}

	/**
	 * Constructor
	 * 
	 * @param key
	 *            key
	 */
	CustomQueriesEnum(String key) {
		this.key = key;
	}

	/**
	 * Obtiene objeto Enum desde texto
	 * 
	 * @param key
	 *            clave de texto a buscar
	 * @return enum
	 */
	public static CustomQueriesEnum fromValue(String key) {
		CustomQueriesEnum em = lookup.get(key);
		if (null == em) {
			return UNKNOWN;
		}
		return em;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

}
