package com.insieme.core.login.rest.handlers;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface LoginResource {
	
	@Get
	public String retrieve();
	
	@Put
	public String getUserName(String userJson);
	
	@Delete
	public void remove();
}
