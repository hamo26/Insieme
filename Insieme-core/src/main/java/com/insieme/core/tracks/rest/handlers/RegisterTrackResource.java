package com.insieme.core.tracks.rest.handlers;

import org.restlet.resource.Post;

import com.insieme.common.domain.dto.InsiemeException;

/**
 * Resource used to register track.
 */
public interface RegisterTrackResource {

	/**
	 * Register track. This is  a database transaction and so if the transaction 
	 * does not succeed, it is rolled back.
	 *
	 * @param trackRepresentation the track representation
	 * @return the registered user
	 */
	@Post
	public String registerTrack(String trackRepresentation) throws InsiemeException;
}
