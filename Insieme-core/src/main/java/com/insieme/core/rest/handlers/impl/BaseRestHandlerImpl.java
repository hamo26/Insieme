package com.insieme.core.rest.handlers.impl;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.google.inject.Guice;
import com.insieme.common.guice.InsiemeCommonModule;
import com.insieme.core.guice.SelfInjectingServerResourceModule;
import com.insieme.core.guice.ServiceModule;
import com.insieme.core.guice.TransactionModule;
import com.insieme.core.tracks.rest.handlers.impl.ArtistTracksResourceImpl;
import com.insieme.core.tracks.rest.handlers.impl.RegisterTrackResourceImpl;
import com.insieme.core.tracks.rest.handlers.impl.TracksResourceImpl;
import com.insieme.core.tracks.rest.handlers.impl.UpdateTrackResourceImpl;
import com.insieme.core.user.rest.handlers.impl.ArtistResourceImpl;
import com.insieme.core.user.rest.handlers.impl.ArtistSearchResourceImpl;
import com.insieme.core.user.rest.handlers.impl.AuthenticationResourceImpl;
import com.insieme.core.user.rest.handlers.impl.RegistrationResourceImpl;

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
        // new instance an Insieme resource. 
        Router router = new Router(getContext());  
        
        Guice.createInjector(
        		new ServiceModule(),
        		new TransactionModule(),
        		new SelfInjectingServerResourceModule(),
        		new InsiemeCommonModule());
        
        // Defines only one route  
        router.attach("user/login", AuthenticationResourceImpl.class);  
        router.attach("/tracks/{trackId}", TracksResourceImpl.class);
        router.attach("/register/track", RegisterTrackResourceImpl.class);
        router.attach("/update/track", UpdateTrackResourceImpl.class);
        router.attach("/register/user", RegistrationResourceImpl.class);
        router.attach("/register/artist", ArtistResourceImpl.class);
        router.attach("/artist/{artistId}", ArtistResourceImpl.class);
        router.attach("/search/artist", ArtistSearchResourceImpl.class);
        router.attach("/tracks/artist/{artistId}", ArtistTracksResourceImpl.class);
        router.attach("/delete/track", UpdateTrackResourceImpl.class);
        return router;  
    }  
}
