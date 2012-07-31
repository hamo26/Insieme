package com.insieme.core.user.rest.handlers;

import org.restlet.resource.Post;

/**
 * The ArtistResource.
 */
public interface ArtistResource {

	@Post
	public String registerArtist(String artistRepresentation);
}
