package com.insieme.common.domain.dto;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import com.insieme.common.util.JSONUtil;

/**
 * Insieme exception that can easily be json-ified and transfered to the client.
 * 
 * TODO: Figure out a way to get rid of the json util being injected and hae the rest handler take care of 
 * 		 deserializing the exception.
 */
public class InsiemeException extends Exception {

	
	private static final long serialVersionUID = -3722788484561737019L;
	
	private final InsiemeExceptionEntity exception;

	private final JSONUtil jsonUtil;
	
	/**
	 * Default constructor.
	 *
	 * @param jsonUtil the json util
	 * @param exception the exception
	 */
	@Inject
	public InsiemeException (@Named("jsonUtil") final JSONUtil jsonUtil, 
			@Assisted final String exception) {
		this.jsonUtil = jsonUtil;
		this.exception = new InsiemeExceptionEntity(exception);
	}
	
	/**
	 * Serialize the json exception.
	 *
	 * @return the string
	 */
	public String serializeJsonException() {
		return jsonUtil.serializeRepresentation(this.exception);
	}

}
