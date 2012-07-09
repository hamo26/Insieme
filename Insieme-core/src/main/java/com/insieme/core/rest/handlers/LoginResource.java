package com.insieme.core.rest.handlers;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class LoginResource extends ServerResource {

	@Get
	public String hello() {
		return "" + getReference() + '\n' + "Root URI      : "  
	            + getRootRef() + '\n' + "Routed part   : "  
	            + getReference().getBaseRef() + '\n' + "Remaining part: "  
	            + getReference().getRemainingPart(); 
	}
	
}
