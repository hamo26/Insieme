package com.insieme.core.rest.handlers.impl;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.insieme.core.rest.handlers.LoginResource;
import com.insieme.core.rest.handlers.TracksServerResource;

public class BaseRestHandlerImpl extends Application {
	
	/** 
     * Creates a root Restlet that will receive all incoming calls. 
     */  
    @Override  
    public synchronized Restlet createInboundRoot() {  
        // Create a router Restlet that routes each call to a  
        // new instance of HelloWorldResource.  
        Router router = new Router(getContext());  
  
        // Defines only one route  
        router.attach("/login", LoginResource.class);  
        router.attach("/tracks/{trackId}", TracksServerResource.class);
  
        return router;  
    }  
}
