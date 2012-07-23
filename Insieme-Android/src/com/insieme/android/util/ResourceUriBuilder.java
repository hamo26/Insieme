package com.insieme.android.util;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ResourceUriBuilder {
	
	private String resourceUri;

	private final String hostUri;

	private final int hostPort;
	
	private String id;
	
	@Inject
	public ResourceUriBuilder(@Named("host") 
							  final String hostUri,
							  @Named("hostPort")
							  final int hostPort) {
		this.hostUri = hostUri;
		this.hostPort = hostPort;
	}
	
	public ResourceUriBuilder setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
		return this;
	}
	
	public ResourceUriBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public String build () {
		assert resourceUri != null : "resource uri must not be null.";
		String resourceResult = "http://"+this.hostUri+":"+String.valueOf(this.hostPort)+"/insieme/"+this.resourceUri;
		return this.id != null 
				? resourceResult+"/"+this.id : resourceResult;
	}
	
}
