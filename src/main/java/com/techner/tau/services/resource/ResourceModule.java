package com.techner.tau.services.resource;

import com.google.inject.AbstractModule;

public class ResourceModule extends AbstractModule {

	@Override
	protected void configure() {
		// this is where the resources are bound
		bind(UserResource.class);
		bind(MeasuresResource.class);
		bind(StationResource.class);
		bind(NotificationResource.class);
		bind(AlertResource.class);
		bind(CustomQueriesResource.class);
		bind(GraphicsResource.class);
		bind(EntityResource.class);
		bind(ReportsResource.class);
		bind(ForecastResource.class);
		bind(OperationResource.class);
	}

}
