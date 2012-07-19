package com.insieme.common.domain.dto;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import com.insieme.common.util.JSONUtil;

/**
 * Insieme exception that can easily be json-ified and transfered to client.
 */
public class InsiemeException extends Exception {

	
	private static final long serialVersionUID = -3722788484561737019L;
	
	private final InsiemeExceptionEntity exception;

	private final JSONUtil jsonUtil;
	
	@Inject
	public InsiemeException (@Named("jsonUtil") final JSONUtil jsonUtil, 
			@Assisted final String exception) {
		this.jsonUtil = jsonUtil;
		this.exception = new InsiemeExceptionEntity(exception);
	}
	
	public String serializeJsonException() {
		return jsonUtil.serializeRepresentation(this.exception);
	}

}
