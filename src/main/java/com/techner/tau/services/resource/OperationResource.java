package com.techner.tau.services.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.entity.User;
import com.techner.tau.services.service.OperationService;
import com.techner.tau.services.validator.Validator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/operations")
@Api(value = "/operations", description = "Servicios para enviar operaciones a estaciones")
public class OperationResource {

	/** servicio de pronostico */
	private final OperationService service;
	/** factory */
	private final ServiceResult.Factory<User> factory;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            servicio
	 */
	@Inject
	public OperationResource(OperationService service, ServiceResult.Factory<User> factory) {
		this.service = service;
		this.factory = factory;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Inserta operaciones para ser enviado a las estaciones", responseClass = "com.techner.tau.common.entity.ServiceResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetro operation vacíos")})
	public ServiceResult<User> insert(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idStation,
			@ApiParam(value = "operation", required = true) @QueryParam("operation") String operation,
			@DefaultValue("false") @ApiParam(value = "force", required = false) @QueryParam("force") boolean force) {
		// Validate
		Validator.notEmpty(operation);
		int resp = service.insertOperation(idStation, operation, force);
		boolean success = (resp == 0) ? false : true;
		return factory.create(success, null);
	}
}
