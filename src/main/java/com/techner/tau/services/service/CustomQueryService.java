package com.techner.tau.services.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.QueryResult;
import com.techner.tau.services.enumeration.CustomQueriesEnum;
import com.techner.tau.services.mapper.QueryMapper;

public class CustomQueryService {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(CustomQueryService.class);
	/** mapper */
	private final QueryMapper mapper;

	/**
	 * Constructor
	 * 
	 * @param mapper
	 */
	@Inject
	public CustomQueryService(QueryMapper mapper) {
		this.mapper = mapper;
	}

	public QueryResult calculateCustomQuery(Integer idSation, CustomQueriesEnum type, String param, Date initDate,
			Date endDate) {
		QueryResult result = mapper.calculateQuery(idSation, type.getKey(), param, initDate, endDate);
		return result;
	}
}
