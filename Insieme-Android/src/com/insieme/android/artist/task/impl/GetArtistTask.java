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
 * Task to get an artist based on artist-id which is directly related to the user id.
 * An artist is a user.
 */
public class GetArtistTask extends AsyncTask<String, Integer, RestResult<ArtistEntity>> {
	private final RestTemplate restTemplate;
	private final ResourceUriBuilder resourceUriBuilder;
	private final RestResultHandler restResultHandler;
	private final String artistResourceUri;
	
	@SuppressWarnings("unchecked")
	@Inject
	public GetArtistTask(@Named("restTemplate") 
							final RestTemplate restTemplate,
							@Named("resourceUriBuilder") 
							final ResourceUriBuilder resourceUriBuilder,
							@Named("artistResource")
							final String artistResourceUri,
							@Named("restResultHandler")
							final RestResultHandler restResultHandler) {
		this.restTemplate = restTemplate;
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.addAll(Arrays.asList(new GsonHttpMessageConverter(), new StringHttpMessageConverter()));

		this.restTemplate.setMessageConverters(messageConverters);
		this.resourceUriBuilder = resourceUriBuilder;
		this.artistResourceUri = artistResourceUri;
		this.restResultHandler = restResultHandler;
	}
	
	@Override
	protected RestResult<ArtistEntity> doInBackground(String... params) {
		String url = resourceUriBuilder
				.setResourceUri(this.artistResourceUri)
				.setId(params[0])
				.build();
		
		
		String jsonResult = this.restTemplate.getForObject(url, String.class);
		return restResultHandler.createRestResult(jsonResult, ArtistEntity.class);
	}

}
