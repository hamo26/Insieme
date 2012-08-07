package com.insieme.core.user.rest.handlers;

import org.restlet.resource.Post;

/**
 * Resource used to authenticate a user.
 */
public interface AuthenticationResource {

	/**
	 * Authenticate a user.
	 *
	 * @param userRepresentation the user representation
	 * @return the string
	 */
	@Post
	public String authenticateUser(String userRepresentation);
}
