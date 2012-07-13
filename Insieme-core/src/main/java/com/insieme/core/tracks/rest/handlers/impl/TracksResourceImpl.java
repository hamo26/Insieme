package com.insieme.core.tracks.rest.handlers.impl;

import org.restlet.resource.ResourceException;

import com.insieme.common.guice.SelfInjectingServerResource;
import com.insieme.core.tracks.rest.handlers.TracksResource;

/**
 * Implementation of {@link TracksResource}.
 */
public class TracksResourceImpl extends SelfInjectingServerResource implements TracksResource {
	
	private String trackId;
	
	@Override
    protected void doInit() throws ResourceException {
        this.trackId =  (String) getRequestAttributes().get("trackId");
    }
	
	public String getTrack() {
		return trackId;
	}
}
