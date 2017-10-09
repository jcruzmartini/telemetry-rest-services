package com.techner.tau.services.resource;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.GraphData;
import com.techner.tau.common.entity.GraphDataResult;
import com.techner.tau.common.entity.ServiceResult;
import com.techner.tau.common.exception.ServiceException;
import com.techner.tau.services.common.DateParam;
import com.techner.tau.services.service.GraphicsService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/graphics")
@Api(value = "/graphics", description = "Pone a disposición información para ser mostrada en gráficos ")
public class GraphicsResource {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(GraphicsResource.class);
    /** service station **/
    private final GraphicsService service;
    /** factory */
    private final ServiceResult.Factory<GraphDataResult> factory;

    private final GraphDataResult.Factory factoryGraphData;

    /**
     * Constructor
     * 
     * @param service
     *            station service
     */
    @Inject
    public GraphicsResource(GraphicsService service, ServiceResult.Factory<GraphDataResult> factory,
            GraphDataResult.Factory factoryGraphData) {
        this.service = service;
        this.factory = factory;
        this.factoryGraphData = factoryGraphData;
    }

    @GET
    @Path("/{id}/evolution")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Obtiene la información para ser mostrada en el gráfico de evolución de las variables", responseClass = "com.techner.tau.common.entity.GraphDataResult")
    @ApiErrors(value = {@ApiError(code = 400, reason = "Id de la estación inválido"),
            @ApiError(code = 500, reason = "Información de la estación no encontrada")})
    public ServiceResult<GraphDataResult> getInfo(
            @ApiParam(value = "Id de la estación", required = true) @PathParam("id") Integer idSation,
            @ApiParam(value = "Fecha desde", required = true) @QueryParam("initDate") DateParam initDate,
            @ApiParam(value = "Fecha hasta", required = false) @QueryParam("endDate") DateParam endDate) {
        ServiceResult<GraphDataResult> result = null;
        logger.info("Buscando datos para la estacion con id  {}", idSation);

        Date iDate = (initDate == null) ? null : initDate.getDate();
        Date eDate = (endDate == null) ? null : endDate.getDate();
        List<GraphData> data = service.getEvolutionData(idSation, iDate, eDate);

        if (data != null) {
            GraphDataResult dataResult = factoryGraphData.create(data);
            result = factory.create(true, dataResult);
        } else {
            throw new ServiceException("Error obteniendo la información para gráficos");
        }

        logger.info("Datos recolectados de la estacion con id  {}", result);
        return result;
    }
}
