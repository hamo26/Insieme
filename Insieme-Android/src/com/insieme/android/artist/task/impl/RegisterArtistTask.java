package com.insieme.android.artist.task.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.android.util.ResourceUriBuilder;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.rest.RestResult;
import com.insieme.common.domain.rest.RestResultHandler;

/**
 * Task to register a User as an Artist.
 */
public class RegisterArtistTask
		extends
		AsyncTask<String, Integer, RestResult<ArtistEntity>> {

	private final RestTemplate restTemplate;
	private final ResourceUriBuilder resourceUriBuilder;
	private final String artistRegistrationResourceUri;
	private final RestResultHandler restResultHandler;
	
	/**
	 * Default Constructor.
	 *
	 * @param restTemplate the rest template
	 * @param resourceUriBuilder the resource uri builder
	 * @param artistRegistrationResourceUri the artist registration resource uri
	 * @param restResultHandler the rest result handler
	 */
	@SuppressWarnings("unchecked")
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
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.addAll(Arrays.asList(new GsonHttpMessageConverter(), new StringHttpMessageConverter()));

		this.restTemplate.setMessageConverters(messageConverters);
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
		
		ArtistEntity postArtist = new ArtistEntity(artistId, artistName, artistGenre);
		
		String jsonResult = this.restTemplate.postForObject(url, postArtist, String.class);
		return restResultHandler.createRestResult(jsonResult, ArtistEntity.class);
	}

}
