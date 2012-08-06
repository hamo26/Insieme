package com.insieme.core.tracks.rest.handlers;

import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.insieme.common.domain.dto.InsiemeException;

/**
 * Resource used to register track.
 */
public interface UpdateTrackResource {

	/**
	 * Update track (mainly the download count). This is a database transaction and so if the transaction does 
	 * not succeed, it is rolled back.
	 *
	 * @param trackRepresentation the track representation
	 * @return the string
	 * @throws InsiemeException the insieme exception
	 */
	@Put
	public String updateTrack(String trackRepresentation) throws InsiemeException;
	
	/**
	 * Deletes a track.
	 *
	 * @param trackRepresentation the track representation
	 * @return the string
	 * @throws InsiemeException the insieme exception
	 */
	@Post
	public String deleteTrack(String trackRepresentation) throws InsiemeException;
}
