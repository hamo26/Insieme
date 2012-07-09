package com.insieme.core.rest.handlers;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class TracksServerResource extends ServerResource implements TracksResource {
	
	private String trackId;
	
	@Override
    protected void doInit() throws ResourceException {
        this.trackId =  (String) getRequestAttributes().get("trackId");
    }
	
	public String getTrack() {
		return trackId;
	}
}
