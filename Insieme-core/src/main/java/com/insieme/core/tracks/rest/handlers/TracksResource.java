package com.insieme.core.tracks.rest.handlers;

import org.restlet.resource.Get;

public interface TracksResource {
	
	@Get
	public String getTrack();

}
