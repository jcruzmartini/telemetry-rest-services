package com.techner.tau.services.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.techner.tau.services.service.ForecastService;
import com.techner.tau.services.validator.Validator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/forecast")
@Api(value = "/forecast", description = "Servicios de Pronostico del tiempo")
public class ForecastResource {

	/** servicio de pronostico */
	private final ForecastService service;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            servicio
	 */
	@Inject
	public ForecastResource(ForecastService service) {
		this.service = service;
	}

	@GET
	@Path("/{latitude}/{longitude}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Brinda pronóstico dependiendo posicion GPS", responseClass = "com.techner.tau.common.entity.ServiceResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetros obligatorios latitude o longitude vacíos")})
	public Response forecast(@ApiParam(value = "Latitude", required = true) @PathParam("latitude") String latitude,
			@ApiParam(value = "Longitude", required = true) @PathParam("longitude") String longitude) {
		// Validate
		Validator.notEmpty(latitude, longitude);
		String json = service.getForecast(latitude, longitude);
		return Response.ok(json).build();
	}
}
