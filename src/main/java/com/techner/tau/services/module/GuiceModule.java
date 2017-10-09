package com.techner.tau.services.module;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebListener;

import org.apache.commons.configuration.ConfigurationException;
import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.techner.tau.common.entity.EntityModule;
import com.techner.tau.common.exception.TAUExceptionModule;
import com.techner.tau.services.config.Config;
import com.techner.tau.services.provider.JacksonProvider;
import com.techner.tau.services.provider.JasyptProvider;
import com.techner.tau.services.resource.ResourceModule;
import com.techner.tau.services.service.AuthenticationTokenService;

@WebListener
public class GuiceModule extends GuiceServletContextListener {

	protected Config config;

	private static Logger logger = LoggerFactory.getLogger(GuiceModule.class);

	/**
	 * Constructor
	 */
	public GuiceModule() {
	}

	@Override
	protected Injector getInjector() {

		logger.debug("Creating GuiceInjector which bootstraps the application");
		return Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				// read configuration values
				String configFile = getServletContext().getInitParameter(Config.APP_CONFIG_LOCATION);
				try {
					config = new Config(configFile);
				} catch (ConfigurationException ex) {
					logger.error("Archivo de configuracion no encontrado. Archivo: {}", configFile, ex);
					throw new IllegalArgumentException("Archivo de configuracion no encontrado", ex);
				}

				bind(Config.class).toInstance(config);

				// Setea las propiedades de la base de datos
				install(new Module() {
					@Override
					public void configure(Binder binder) {
						Names.bindProperties(binder, config.getDatabaseProperties());
					}
				});

				// add the domain and mapper classes
				install(new EntityModule());
				install(new TAUMyBatisModule());
				install(new TAUExceptionModule());
				install(new ResourceModule());
				bind(JacksonJaxbJsonProvider.class).toProvider(JacksonProvider.class).in(Scopes.SINGLETON);
				bind(PBEStringCleanablePasswordEncryptor.class).toProvider(JasyptProvider.class).in(Scopes.SINGLETON);
				bind(AuthenticationTokenService.class).in(Scopes.SINGLETON);
				bind(Client.class).toProvider(com.techner.tau.services.provider.JerseyClientProvider.class).in(
						Scopes.SINGLETON);

				Map<String, String> params = new HashMap<String, String>();
				params.put("com.sun.jersey.spi.container.ContainerResponseFilters",
						"com.techner.tau.services.filter.JsonpCallbackFilter");
				params.put("com.sun.jersey.spi.container.ContainerRequestFilters",
						"com.techner.tau.services.filter.AuthenticationTokenFilter");
				params.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
				params.put(ServletContainer.PROPERTY_WEB_PAGE_CONTENT_REGEX, "/apidocs/.*");
				filterRegex("(.)*reports(.)*", "(.)*entity(.)*", "(.)*graphics(.)*", "(.)*custom-queries(.)*",
						"(.)*alerts(.)*", "(.)*notifications(.)*", "(.)*stations(.)*", "(.)*users(.)*",
						"(.)*measures(.)*", "(.)*forecast(.)*", "(.)*operation(.)*").through(GuiceContainer.class,
						params);
			}
		});
	}
}
