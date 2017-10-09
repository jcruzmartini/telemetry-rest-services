package com.techner.tau.services.common;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import com.techner.tau.common.exception.ServiceException;

public class ListInteger extends ListQueryParam<Integer> {

	/**
	 * Constructor
	 * 
	 * @param listStr
	 * @throws WebApplicationException
	 */
	public ListInteger(String listStr) throws WebApplicationException {
		super(listStr);
	}

	@Override
	public List<Integer> construcList(String[] array) {
		try {
			this.list = new ArrayList<Integer>();
			for (String num : array) {
				list.add(Integer.parseInt(num));
			}
		} catch (Exception e) {
			throw new ServiceException(String.format("Error parseando lista [%s] pasada como parametro", array), e);
		}
		return list;
	}

}