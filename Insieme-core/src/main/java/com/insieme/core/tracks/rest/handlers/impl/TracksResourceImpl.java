package com.insieme.core.tracks.rest.handlers.impl;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.insieme.core.tracks.rest.handlers.TracksResource;

public class TracksResourceImpl extends ServerResource implements TracksResource {
	
	private String trackId;
	
	@Override
    protected void doInit() throws ResourceException {
        this.trackId =  (String) getRequestAttributes().get("trackId");
    }
	
	public String getTrack() {
		return trackId;
	}
}
