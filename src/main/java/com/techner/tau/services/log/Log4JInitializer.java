package com.techner.tau.services.log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.xml.DOMConfigurator;

import com.techner.tau.services.config.Config;

/**
 * Initialize Log4J by reading a XML based configuration file Also supports
 * reloading (takes 60 seconds for changes to take effect)
 */
@WebListener
public class Log4JInitializer implements ServletContextListener {

	/**
	 * Location of configuration file
	 */
	private String configFile;

	/**
	 * Invoked at application start up - initialize log4j
	 * 
	 * @param sce
	 *            ServletContextEvent
	 */
	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		configFile = sce.getServletContext().getInitParameter(Config.LOG4J_CONFIG_LOCATION);
		DOMConfigurator.configureAndWatch(configFile);
	}

	/**
	 * Invoked at application shutdown
	 * 
	 * @param sce
	 *            ServletContextEvent
	 */
	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		// do nothing here
	}
}
