package com.techner.tau.services.provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.inject.Provider;
import com.techner.tau.common.module.JacksonModule;

public class JacksonProvider implements Provider<JacksonJaxbJsonProvider> {

	@Override
	public JacksonJaxbJsonProvider get() {
		// Add Json mapped classes here
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.registerModule(new JacksonModule());
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(mapper);
		return provider;
	}

}
