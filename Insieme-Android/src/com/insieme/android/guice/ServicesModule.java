package com.insieme.android.guice;

import org.springframework.web.client.RestTemplate;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.insieme.android.user.service.impl.LoginTask;
import com.insieme.android.user.service.impl.RegisterTask;

public class ServicesModule extends AbstractModule {

	@Override
	protected void configure() {
		//Spring rest client bindings.
		bind(RestTemplate.class).annotatedWith(Names.named("restTemplate")).to(RestTemplate.class);
		
		//service bindings
		bind(LoginTask.class).annotatedWith(Names.named("loginTask")).to(LoginTask.class);
		bind(RegisterTask.class).annotatedWith(Names.named("registerTask")).to(RegisterTask.class);
	}

}
