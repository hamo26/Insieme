package com.insieme.core.tracks.rest.handlers;

import org.restlet.resource.Get;

/**
 * Resource used to get track information.
 */
public interface TracksResource {
	
	/**
	 * Gets a track given a track id.
	 *
	 * @return the track
	 */
	@Get
	public String getTrack();

}
