package com.insieme.core.rest.handlers.impl;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.google.inject.Guice;
import com.insieme.common.guice.SelfInjectingServerResourceModule;
import com.insieme.common.guice.ServiceModule;
import com.insieme.common.guice.TransactionModule;
import com.insieme.core.login.rest.handlers.impl.LoginResourceImpl;
import com.insieme.core.tracks.rest.handlers.impl.TracksResourceImpl;

/**
 * The Base Rest Handler which defines all url binding and delegates to the appropriate resource.
 * 
 * TO DO: look at a cleaner way of binding. This method isn't bad but could do better.
 *
 */
public class BaseRestHandlerImpl extends Application {
	
	public BaseRestHandlerImpl() {
		
	}
	
	/** 
     * Creates a root Restlet that will receive all incoming calls. 
     */  
    @Override  
    public synchronized Restlet createInboundRoot() {  
        // Create a router Restlet that routes each call to a  
        // new instance of HelloWorldResource.  
        Router router = new Router(getContext());  
        
        Guice.createInjector(
        		new ServiceModule(),
        		new TransactionModule(),
        		new SelfInjectingServerResourceModule());
        
        // Defines only one route  
        router.attach("/login/{userId}", LoginResourceImpl.class);  
        router.attach("/tracks/{trackId}", TracksResourceImpl.class);
  
        return router;  
    }  
}
