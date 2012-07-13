package com.insieme.core.rest.handlers.impl;

import org.restlet.Component;
import org.restlet.data.Protocol;


/**
 * Java RestletDriver until I can figure out a way to move this into a maven webapp container.
 */
public class RestletDriver {
	public static void main(String[] args) throws Exception{
		 // Create a new Component.  
	    final Component component = new Component();  
	  
	    // Add a new HTTP server listening on port 8182.  
	    component.getServers().add(Protocol.HTTP, 8184);  
	  
	    // Attach the sample application.  
	    component.getDefaultHost().attach("/insieme",  
	            new BaseRestHandlerImpl());  
	  
	    // Start the component.  
	    component.start(); 
	}
	
}
