package com.insieme.core.tracks.rest.handlers;

import org.restlet.resource.Get;

/**
 * Resource to get tracks associated with artists.
 */
public interface ArtistTracksResource {
	
	/**
	 * Gets the tracks associated with an artist.
	 *
	 * @return the tracks for artist
	 */
	@Get
	public String getTracksForArtist();

}
