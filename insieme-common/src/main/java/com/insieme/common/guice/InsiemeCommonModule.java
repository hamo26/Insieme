package com.insieme.common.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.insieme.common.util.JSONUtil;


public class InsiemeCommonModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(JSONUtil.class).annotatedWith(Names.named("jsonUtil")).to(JSONUtil.class);
	}

}
