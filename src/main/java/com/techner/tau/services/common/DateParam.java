package com.techner.tau.services.common;

import java.util.Date;

import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang3.StringUtils;

import com.techner.tau.common.exception.ServiceException;

public class DateParam {
	private final Date date;

	public DateParam(String dateStr) throws WebApplicationException {
		if (StringUtils.isEmpty(dateStr)) {
			this.date = null;
			return;
		}
		try {
			long timestamp = Long.parseLong(dateStr);
			this.date = new Date(timestamp);
		} catch (NumberFormatException e) {
			throw new ServiceException("Error parseando fecha pasada por parametro", e);
		}
	}

	public Date getDate() {
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DateParam [date=" + date + "]";
	}

}