package com.insieme.core.rest.handlers;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.insieme.core.login.rest.handlers.LoginResource;

public class LoginResourceImpl extends ServerResource implements LoginResource {

	@Get
	public String retrieve() {
		return "username";
	}

	@Put
	public String getUserName(String userJson) {
		return null;
	}

	@Delete
	public void remove() {
		
	}

	
	
}
