package com.techner.tau.services.config;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * Clase base para todas las configuraciones necesarias
 * 
 */
public class Config {

	/**
	 * C3p0 DataSource Properties
	 * */

	private final String C3P0_ADQUIRE_RETRIES_ATTEMPTS = "c3p0.acquireRetryAttempts";
	private final String C3P0_MAX = "c3p0.maxPoolSize";
	private final String C3P0_MIN = "c3p0.minPoolSize";
	private final String C3P0_IDLE_TEST_PERIOD = "c3p0.idleConnectionTestPeriod";
	private final String C3P0_ADQUIRE_INC = "c3p0.acquireIncrement";
	private final String C3P0_TEST_QUERY = "c3p0.preferredTestQuery";
	private final String C3P0_MAX_STATEMENTS = "c3p0.maxStatements";
	private final String C3P0_CONNECTION_TIMEOUT = "c3p0.unreturnedConnectionTimeout";
	/**
	 * Configuration
	 */
	protected Configuration config;
	/** database connection properties */
	private final Properties databaseProperties;
	/** ubicacion del config file sacado del web.xml */
	public static final String APP_CONFIG_LOCATION = "app.config.location";
	/** ubicacion del config file sacado del web.xml */
	public static final String LOG4J_CONFIG_LOCATION = "log4j.config.location";
	/**
	 * Reload
	 */
	private static final int RELOAD = 60000;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            File to read configuration settings from
	 * @throws ConfigurationException
	 *             in case of failure
	 */
	public Config(String file) throws ConfigurationException {
		this(new File(file));
	}

	/**
	 * Constructor
	 * 
	 * @param file
	 *            File to read config settings from.
	 * @throws ConfigurationException
	 *             in case of failure
	 */
	public Config(File file) throws ConfigurationException {
		if (!file.exists()) {
			throw new IllegalArgumentException("Archivo especificado inexistente");
		}
		XMLConfiguration xmlConfig = new XMLConfiguration(file);
		FileChangedReloadingStrategy reload = new FileChangedReloadingStrategy();
		reload.setRefreshDelay(RELOAD); // Check at most once every minute.
		xmlConfig.setReloadingStrategy(reload);
		config = new CompositeConfiguration(xmlConfig);
		databaseProperties = loadDatabaseProperties();
	}

	/***
	 * Retorna el endpoint de YR.NO para forecast
	 * @return endpoint
	 */
	public String getYREndpoint() {
	        return config.getString("endpoint.yr");
	}

	/**
	 * Obtiene la máxima edad permitida del token
	 * 
	 * @return edad en milisegundos
	 */
	public long getTokenAge() {
		return config.getLong("token.age", 3600000l);
	}

	/**
	 * Determina si la seguridad esta habilitada o no
	 * 
	 * @return true, si está habilitada, no en otro caso
	 */
	public boolean isSecurityEnable() {
		return config.getBoolean("token.enable", false);
	}

	/**
	 * Retorna las propiedades de la base de datos
	 * 
	 * @return lista de paths liberados
	 */
	public Set<String> getDMZPaths() {
		String[] paths = config.getStringArray("dmz.path");
		return new HashSet<String>(Arrays.asList(paths));
	}
	
	 /**
         * Retorna las operaciones permitidas
         * 
         * @return lista de operaciones permitidas
         */
        public Set<String> getAllowedOperations() {
                String[] operations = config.getStringArray("operations-allowed.operation");
                return new HashSet<String>(Arrays.asList(operations));
        }

	/**
	 * Retorna los token comodines
	 * 
	 * @return lista tokens
	 */
	public Set<String> getWildcarTokens() {
		String[] tokens = config.getStringArray("token.wildcar.value");
		return new HashSet<String>(Arrays.asList(tokens));
	}

	/**
	 * Retorna las propiedades de la base de datos
	 * 
	 * @return the databaseProperties
	 */
	public Properties getDatabaseProperties() {
		return databaseProperties;
	}

	/**
	 * Carga los properties de la BD desde el archivo XML
	 * 
	 * @return properties
	 */
	private Properties loadDatabaseProperties() {
		Properties properties = new Properties();
		properties.setProperty("JDBC.driver", config.getString("database.driver", "com.mysql.jdbc.Driver"));
		properties.setProperty("JDBC.url", config.getString("database.url", "jdbc:mysql://localhost/smsbd"));
		properties.setProperty("JDBC.username", config.getString("database.username", "root"));
		properties.setProperty("JDBC.password", config.getString("database.password", "root"));
		properties.setProperty("JDBC.autoCommit", config.getString("database.autoCommit", "true"));
		properties.setProperty("mybatis.environment.id", config.getString("database.environment.id", "development"));

		properties.setProperty(C3P0_ADQUIRE_RETRIES_ATTEMPTS,
				config.getString("database.c3p0.acquireRetryAttempts", "1"));
		properties.setProperty(C3P0_MAX, config.getString("database.c3p0.maxPoolSize", "20"));
		properties.setProperty(C3P0_MIN, config.getString("database.c3p0.minPoolSize", "5"));
		properties.setProperty(C3P0_MAX_STATEMENTS, config.getString("database.c3p0.maxStatements", "155"));
		properties
				.setProperty(C3P0_IDLE_TEST_PERIOD, config.getString("database.c3p0.idleConnectionTestPeriod", "100"));
		properties.setProperty(C3P0_ADQUIRE_INC, config.getString("database.c3p0.acquireIncrement", "1"));
		properties.setProperty(C3P0_TEST_QUERY, config.getString("database.c3p0.preferredTestQuery", "SELECT 1;"));
		properties.setProperty(C3P0_CONNECTION_TIMEOUT,
				config.getString("database.c3p0.unreturnedConnectionTimeout", "5000"));

		return properties;
	}

}
