package com.insieme.core.user.rest.handlers;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * The ArtistResource.
 */
public interface ArtistResource {

	/**
	 * Registers an artist.
	 *
	 * @param artistRepresentation the artist representation
	 * @return the string
	 */
	@Post
	public String registerArtist(String artistRepresentation);
	
	/**
	 * Gets the artist.
	 *
	 * @return the artist
	 */
	@Get
	public String getArtist();
}
