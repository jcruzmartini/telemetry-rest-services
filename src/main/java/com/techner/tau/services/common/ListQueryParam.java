package com.techner.tau.services.common;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang3.StringUtils;

public abstract class ListQueryParam<T> {
	/** lista de T */
	protected List<T> list;

	public ListQueryParam(String listStr) throws WebApplicationException {
		if (StringUtils.isEmpty(listStr)) {
			this.list = new ArrayList<T>();
			return;
		} else {
			list = construcList(StringUtils.split(listStr, ","));
		}
	}

	public abstract List<T> construcList(String[] split);

	public List<T> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return list.toString();
	}

}