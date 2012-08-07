package com.insieme.android.guice;

import org.springframework.web.client.RestTemplate;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.insieme.android.artist.task.impl.FindArtistsTask;
import com.insieme.android.artist.task.impl.GetArtistTask;
import com.insieme.android.artist.task.impl.RegisterArtistTask;
import com.insieme.android.user.task.impl.LoginTask;
import com.insieme.android.user.task.impl.RegisterTask;

/**
 * Guice module for service bindings.
 */
public class ServicesModule extends AbstractModule {

	@Override
	protected void configure() {
		//Spring rest client bindings.
		bind(RestTemplate.class).annotatedWith(Names.named("restTemplate")).to(RestTemplate.class);
		
		//service bindings
		//TODO: these bindings are unnecessary because the Guice provider can find the classes without
		//      annotations. Look into deleting these at some point.
		bind(LoginTask.class).annotatedWith(Names.named("loginTask")).to(LoginTask.class);
		bind(RegisterTask.class).annotatedWith(Names.named("registerTask")).to(RegisterTask.class);
		bind(FindArtistsTask.class).annotatedWith(Names.named("findArtistTask")).to(FindArtistsTask.class);
		bind(GetArtistTask.class).annotatedWith(Names.named("getArtistTask")).to(GetArtistTask.class);
		bind(RegisterArtistTask.class).annotatedWith(Names.named("registerArtistTask")).to(RegisterArtistTask.class);
	}

}
