package com.insieme.common.domain.dto;

import com.google.gson.annotations.SerializedName;


/**
 * Insieme exception dto to pass json exceptions to the client. May be completely unnecessary.
 */
public class InsiemeExceptionEntity extends RestEntity {
	
	@SerializedName("insieme-error")
	private final String exception;
	
	/**
	 * Default constructor.
	 *
	 * @param exception the exception
	 */
	public InsiemeExceptionEntity(final String exception) {
		this.exception = exception;
	}
	
	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	public String getException() {
		return this.exception;
	}
}
