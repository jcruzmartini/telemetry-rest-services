package com.techner.tau.services.filter;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * Jersey filter for easy JSONP support.
 * 
 * To access this filter, add the following to your web.xml or @WebListener
 * class param: "com.sun.jersey.spi.container.ContainerResponseFilters" value:
 * "com.g2llc.jersey.filter.JsonpCallbackFilter"
 * 
 * Then simply pass in the query parameter callback=<function name> to any
 * Jersey supported end point.
 * 
 * Prior to this class, the JSONP support was handled at the resource layer,
 * which has a huge negative of forcing all your methods to return an Object.
 * With this filter your methods can now return their actual response classes.
 * 
 * E.g.
 * 
 * @GET ManagerResponse<ManagerStatus> get(@QueryParam("id") id);
 */
public class JsonpCallbackFilter implements ContainerResponseFilter {
	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		List<String> callback = request.getQueryParameters().get("callback");
		if (callback == null || callback.size() != 1 || callback.get(0).isEmpty()) {
			return response;
		}

		ResponseBuilder rb = Response.fromResponse(response.getResponse());
		rb.header("Content-Type", "application/javascript");
		response.setResponse(rb.entity(new JSONPObject(callback.get(0), response.getEntity())).build());
		return response;
	}
}