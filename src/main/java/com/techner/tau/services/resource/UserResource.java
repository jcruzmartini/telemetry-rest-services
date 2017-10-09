package com.techner.tau.services.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.ServiceRequest;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.entity.User;
import com.techner.tau.services.service.UserService;
import com.techner.tau.services.validator.Validator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/users")
@Api(value = "/users", description = "Operaciones relacionadas con usuarios ")
public class UserResource {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
	/** servicio de usuario */
	private final UserService service;
	/** factory */
	private final ServiceResult.Factory<User> factory;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            servicio
	 */
	@Inject
	public UserResource(UserService service, ServiceResult.Factory<User> factory) {
		this.service = service;
		this.factory = factory;
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Realiza login de un usuario", responseClass = "com.techner.tau.common.entity.User")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetros obligatorios email o password vacíos")})
	public ServiceResult<User> login(
			@ApiParam(value = "Información del usuario", required = true) ServiceRequest<User> request) {
		ServiceResult<User> result = null;
		User userReq = request.getRequest();
		// Validate
		Validator.notEmpty(userReq.getEmail(), userReq.getPassword());

		logger.info("Autenticando usuario {}", userReq);
		User user = service.login(request.getRequest());
		if (user != null) {
			result = factory.create(true, user);
		} else {
			result = factory.create(false, null);
		}

		logger.info("Resultado de la autenticación {}", result);
		return result;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Realiza el update de una estación", responseClass = "com.techner.tau.common.entity.User")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parámetro obligatorio id vacío")})
	public ServiceResult<User> update(
			@ApiParam(value = "Información del usuario", required = true) ServiceRequest<User> request) {
		User userReq = request.getRequest();

		// Validate
		Validator.notNull(userReq.getId());

		logger.info("Actualizando usuario {}", userReq);
		service.insertOrUpdate(userReq);
		logger.info("Usuario actualizado correctamente");
		return factory.create(true, null);
	}
}
