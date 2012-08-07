package com.insieme.core.user.rest.handlers;

import org.restlet.resource.Post;

import com.insieme.common.database.transactions.Transaction;

/**
 * Resource used to register a user.
 */
public interface RegistrationResource {

	/**
	 * Registers a user.This is  a database transaction and so if the transaction 
	 * does not succeed, it is rolled back.
	 *
	 * @param userRepresentation the user representation
	 * @return the string
	 */
	@Post
	@Transaction
	public String registerUser(String userRepresentation);
}
