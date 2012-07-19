package com.insieme.common.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.rest.RestResultHandler;
import com.insieme.common.util.JSONUtil;


public class InsiemeCommonModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(JSONUtil.class).annotatedWith(Names.named("jsonUtil")).to(JSONUtil.class);
		bind(RestResultHandler.class).annotatedWith(Names.named("restResultHandler")).to(RestResultHandler.class);
		install(new FactoryModuleBuilder()
		 .implement(InsiemeException.class, InsiemeException.class)
	     .build(InsiemeExceptionFactory.class));
	}

}
