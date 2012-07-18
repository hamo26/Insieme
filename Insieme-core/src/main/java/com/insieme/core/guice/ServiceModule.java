package com.insieme.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.insieme.core.user.service.UserService;
import com.insieme.core.user.service.impl.UserServiceImpl;

/**
 * Guice model to track all resource bindings.
 */
public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserService.class).annotatedWith(Names.named("userService")).to(UserServiceImpl.class);
	}

}
