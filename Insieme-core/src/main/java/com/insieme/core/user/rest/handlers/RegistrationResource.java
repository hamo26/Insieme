package com.insieme.core.user.rest.handlers;

import org.restlet.resource.Post;

/**
 * The Registration Resource.
 */
public interface RegistrationResource {

	@Post
	public String registerUser(String userRepresentation);
}
