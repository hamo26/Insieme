package com.insieme.core.user.rest.handlers;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * Resource to register and get artist information.
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
