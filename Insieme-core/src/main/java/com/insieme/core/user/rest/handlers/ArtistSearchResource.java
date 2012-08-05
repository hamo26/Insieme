package com.insieme.core.user.rest.handlers;

import org.restlet.resource.Post;

/**
 * Resource used to search for artsists.
 */
public interface ArtistSearchResource {
	
	/**
	 * Gets artists based on name.
	 *
	 * @param artistSearchRepresentation the artist search representation
	 * @return the artists
	 */
	@Post
	public String getArtists(String artistSearchRepresentation);

}
