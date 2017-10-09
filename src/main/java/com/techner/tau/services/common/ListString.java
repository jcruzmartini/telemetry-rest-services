package com.techner.tau.services.common;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import scala.actors.threadpool.Arrays;

import com.techner.tau.common.exception.ServiceException;

public class ListString extends ListQueryParam<String> {

	/**
	 * Constructor
	 * 
	 * @param listStr
	 * @throws WebApplicationException
	 */
	public ListString(String listStr) throws WebApplicationException {
		super(listStr);
	}

	@Override
	public List<String> construcList(String[] array) {
		try {
			this.list = new ArrayList<String>(Arrays.asList(array));
		} catch (Exception e) {
			throw new ServiceException(String.format("Error parseando lista [%s] pasada como parametro", array), e);
		}
		return list;
	}

}