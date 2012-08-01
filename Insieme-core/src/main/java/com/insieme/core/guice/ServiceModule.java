package com.insieme.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.insieme.core.track.service.TrackService;
import com.insieme.core.track.service.impl.TrackServiceImpl;
import com.insieme.core.user.service.UserService;
import com.insieme.core.user.service.impl.UserServiceImpl;

/**
 * Guice model to track all database service bindings.
 */
public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserService.class).annotatedWith(Names.named("userService")).to(UserServiceImpl.class);
		bind(TrackService.class).annotatedWith(Names.named("trackService")).to(TrackServiceImpl.class);
	}

}
