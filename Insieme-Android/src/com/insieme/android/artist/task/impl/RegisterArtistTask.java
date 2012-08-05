package com.insieme.android.artist.task.impl;

import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.android.util.ResourceUriBuilder;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.rest.RestResult;
import com.insieme.common.domain.rest.RestResultHandler;

public class RegisterArtistTask
		extends
		AsyncTask<String, Integer, RestResult<ArtistEntity>> {

	private final RestTemplate restTemplate;
	private final ResourceUriBuilder resourceUriBuilder;
	private final String artistRegistrationResourceUri;
	private final RestResultHandler restResultHandler;
	
	@Inject
	public RegisterArtistTask(@Named("restTemplate") 
							final RestTemplate restTemplate,
							@Named("resourceUriBuilder") 
							final ResourceUriBuilder resourceUriBuilder,
							@Named("artistRegistrationResource")
							final String artistRegistrationResourceUri,
							@Named("restResultHandler")
							final RestResultHandler restResultHandler) {
		this.restTemplate = restTemplate;
		this.resourceUriBuilder = resourceUriBuilder;
		this.artistRegistrationResourceUri = artistRegistrationResourceUri;
		this.restResultHandler = restResultHandler;
		
	}
	@Override
	protected RestResult<ArtistEntity> doInBackground(String... params) {
		String url = resourceUriBuilder
					.setResourceUri(this.artistRegistrationResourceUri)
					.build();
		String artistId = params[0];
		String artistName = params[1];
		String artistGenre = params[2];
		
		ArtistEntity postArtist = new ArtistEntity(artistId, artistGenre, artistName);
		
		String jsonResult = this.restTemplate.postForObject(url, postArtist, String.class);
		return restResultHandler.createRestResult(jsonResult, ArtistEntity.class);
	}

}
