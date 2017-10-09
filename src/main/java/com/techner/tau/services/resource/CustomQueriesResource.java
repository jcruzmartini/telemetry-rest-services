package com.techner.tau.services.resource;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.QueryResult;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.exception.ServiceException;
import com.techner.tau.common.exception.ValidationException;
import com.techner.tau.services.common.DateParam;
import com.techner.tau.services.enumeration.CustomQueriesEnum;
import com.techner.tau.services.service.CustomQueryService;
import com.techner.tau.services.validator.Validator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/custom-queries")
@Api(value = "/custom-queries", description = "Consultas especiales")
public class CustomQueriesResource {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(CustomQueriesResource.class);
	/** service notifications **/
	private final CustomQueryService service;
	/** factory */
	private final ServiceResult.Factory<QueryResult> factory;

	/**
	 * Constructor
	 * 
	 * @param service
	 *            station service
	 */
	@Inject
	public CustomQueriesResource(CustomQueryService service, ServiceResult.Factory<QueryResult> factory) {
		this.service = service;
		this.factory = factory;
	}

	@GET
	@Path("/{id}/query")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Realiza calculos especiales dependiendo el tipo de query proporcionado", responseClass = "com.techner.tau.common.entity.QueryResult")
	@ApiErrors(value = {@ApiError(code = 400, reason = "Parametros inválidos"),
			@ApiError(code = 500, reason = "Error realizando calculos especiales")})
	public ServiceResult<QueryResult> calculate(
			@ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation,
			@ApiParam(value = "Fecha desde", required = true) @QueryParam("initDate") DateParam initDate,
			@ApiParam(value = "Fecha hasta", required = false) @QueryParam("endDate") DateParam endDate,
			@ApiParam(value = "Tipo de consulta", required = true, allowableValues = "grados-dia,lluvia-acumulada,horas-luz,horas-frio,evapotranspiration") @QueryParam("type") String type,
			@ApiParam(value = "Parametro auxiliar de consulta", required = false) @QueryParam("param") String param) {
		ServiceResult<QueryResult> result = null;
		Validator.notNull(initDate, type);
		logger.info(String
				.format("Calculando query del tipo [%s] para la estación [%s] con param [%s]. Desde fecha [%s] hasta fecha [%s]",
						type, idSation, param, initDate, endDate));
		CustomQueriesEnum enumeration = CustomQueriesEnum.fromValue(type);
		if (enumeration == CustomQueriesEnum.UNKNOWN) {
			logger.error("Error parametro type con valor {} no es válido", type);
			throw new ValidationException(String.format("Error parametro type con valor %s no es válido", type));
		}

		// Fecha inicio
		Date iDate = initDate.getDate();
		// Fecha Fin
		Date fDate = (endDate == null) ? null : endDate.getDate();
		QueryResult queryResult = service.calculateCustomQuery(idSation, enumeration, param, iDate, fDate);

		if (queryResult != null) {
			result = factory.create(true, queryResult);
		} else {
			throw new ServiceException("Error calculando queries especiales");
		}

		logger.info("Resultado de query computada {} ", queryResult);
		return result;
	}
}
