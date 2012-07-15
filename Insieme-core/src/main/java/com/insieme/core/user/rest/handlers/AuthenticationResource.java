package com.insieme.core.user.rest.handlers;

import org.restlet.resource.Post;

/**
 * The LoginResource.
 */
public interface AuthenticationResource {

	@Post
	public String authenticateUser(String userRepresentation);
}
