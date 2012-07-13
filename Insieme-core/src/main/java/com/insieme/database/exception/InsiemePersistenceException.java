package com.insieme.database.exception;

/**
 * Persistence exceptions thrown by services when unexpected errors occur.
 */
public class InsiemePersistenceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1355547548913027605L;

	/**
	 * Default constructor.
	 *
	 * @param exception the exception
	 */
	public InsiemePersistenceException(String exception) {
		super(exception);
	}
}
