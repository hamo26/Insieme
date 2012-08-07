package com.insieme.common.domain.dto;

/**
 * Factory wired into Guice to instantiate insieme exception.
 */
public interface InsiemeExceptionFactory {
	InsiemeException createInsiemeException(String exception);
}
