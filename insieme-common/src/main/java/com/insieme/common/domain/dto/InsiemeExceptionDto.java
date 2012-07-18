package com.insieme.common.domain.dto;


/**
 * Insieme exception dto to pass json exceptions to the client. May be completely unnecessary.
 */
public class InsiemeExceptionDto extends DTOEntity {
	
	private final String exception;
	
	public InsiemeExceptionDto(final String exception) {
		this.exception = exception;
	}
	
	public String getException() {
		return this.exception;
	}
}
