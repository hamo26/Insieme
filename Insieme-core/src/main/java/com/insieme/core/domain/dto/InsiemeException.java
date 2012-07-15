package com.insieme.core.domain.dto;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.util.json.JSONUtil;

/**
 * Insieme exception that can easily be json-ified and transfered to client.
 */
public class InsiemeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3722788484561737019L;

	@Inject
	@Named("jsonUtil")
	private JSONUtil jsonUtil;
	
	private final InsiemeExceptionDto exception;
	
	public InsiemeException (final String exception) {
		this.exception = new InsiemeExceptionDto(exception);
	}
	
	public String serializeJsonException() {
		return jsonUtil.serializeRepresentation(this.exception);
	}

}
