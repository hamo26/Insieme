package com.insieme.core.domain.dto;

import com.insieme.common.database.dto.PersistentEntity;

/**
 * Insieme exception dto to pass json exceptions to the client. May be completely unnecessary.
 */
public class InsiemeExceptionDto extends PersistentEntity {
	
	private final String exception;
	
	public InsiemeExceptionDto(final String exception) {
		this.exception = exception;
	}
	
	public String getException() {
		return this.exception;
	}
}
