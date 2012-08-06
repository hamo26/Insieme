package com.insieme.android.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.insieme.android.util.ResourceUriBuilder;

public class ResourceUriModule extends AbstractModule {

	@Override
	protected void configure() {
		//System specific bindings. 
		//TODO: should put these in properties files.
		bind(String.class).annotatedWith(Names.named("host")).toInstance("192.168.0.60");
		bind(Integer.class).annotatedWith(Names.named("hostPort")).toInstance(8184);
		
		//Resource bindings.
		bind(String.class).annotatedWith(Names.named("authenticationResource")).toInstance("user/login");
		bind(String.class).annotatedWith(Names.named("registrationResource")).toInstance("register/user");
		bind(String.class).annotatedWith(Names.named("searchArtistResource")).toInstance("search/artist");
		bind(String.class).annotatedWith(Names.named("artistResource")).toInstance("artist");
		bind(String.class).annotatedWith(Names.named("artistRegistrationResource")).toInstance("register/artist");
		bind(String.class).annotatedWith(Names.named("artistTracksResource")).toInstance("tracks/artist");
		bind(String.class).annotatedWith(Names.named("updateTracksResource")).toInstance("update/track");
		bind(String.class).annotatedWith(Names.named("deleteTracksResource")).toInstance("delete/track");
		bind(String.class).annotatedWith(Names.named("registerTrackResource")).toInstance("register/track");
		
		//Util Bindings
		bind(ResourceUriBuilder.class).annotatedWith(Names.named("resourceUriBuilder")).to(ResourceUriBuilder.class);
		
		
	}

}
