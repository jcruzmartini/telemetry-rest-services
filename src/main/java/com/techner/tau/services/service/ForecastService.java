package com.techner.tau.services.service;

import java.net.HttpURLConnection;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.techner.tau.common.exception.ServiceException;
import com.techner.tau.services.config.Config;

public class ForecastService {

	/** slf4j logger */
	private static Logger logger = LoggerFactory.getLogger(ForecastService.class);
	/** Jersey client */
	private final Client client;
	/**Endpoint Yr.NO*/
	private final String endpoint;
	

	/**
	 * @param userMapper
	 */
	@Inject
	public ForecastService(Client client, Config config) {
		this.client = client;
		this.endpoint = config.getYREndpoint();
	}

	public String getForecast(String latitude, String longitude) {
		logger.info("Llamando al servicio de pronostico para la posición ({},{})", latitude, longitude);
		String json = "";
		String end = String.format(endpoint,latitude, longitude);

		ClientResponse response = null;
		try {
			WebResource resource = client.resource(end);
			response = resource.accept(MediaType.APPLICATION_XML_TYPE).get(ClientResponse.class);
		} catch (Exception e) {
			String msg = String.format("Error llamando al servicio {}", end);
			logger.error(msg, e);
			throw new ServiceException(msg);
		}

		if (response != null && (response.getStatus() == HttpURLConnection.HTTP_OK || 
		        response.getStatus() ==  HttpURLConnection.HTTP_NOT_AUTHORITATIVE)) {
			String xml = response.getEntity(String.class);
			logger.debug("Respuesta del servicio {}", xml);
			try {
				json = org.json.XML.toJSONObject(xml).toString();
			} catch (JSONException e) {
				logger.error("Error parseando XML to JSON", e);
				throw new ServiceException("Error convirtiendo respuesta a JSON");
			}
		} else {
		    logger.error("Error en la respuesta al servicio de pronóstico. {}", response);
		}
		return json;
	}

}
