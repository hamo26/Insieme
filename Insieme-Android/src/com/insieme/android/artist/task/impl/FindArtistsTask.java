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
import com.insieme.common.domain.dto.ArtistListEntity;
import com.insieme.common.domain.rest.RestResult;
import com.insieme.common.domain.rest.RestResultHandler;

/**
 * Task to find artists based on name and genre.
 */
public class FindArtistsTask extends AsyncTask<String, Integer, RestResult<ArtistListEntity>> {

	private final RestTemplate restTemplate;
	private final ResourceUriBuilder resourceUriBuilder;
	private final RestResultHandler restResultHandler;
	private final String searchArtistResourceUri;
	
	@SuppressWarnings("unchecked")
	@Inject
	public FindArtistsTask(@Named("restTemplate") 
							final RestTemplate restTemplate,
							@Named("resourceUriBuilder") 
							final ResourceUriBuilder resourceUriBuilder,
							@Named("searchArtistResource")
							final String searchArtistResourceUri,
							@Named("restResultHandler")
							final RestResultHandler restResultHandler) {
		this.restTemplate = restTemplate;
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.addAll(Arrays.asList(new GsonHttpMessageConverter(), new StringHttpMessageConverter()));

		this.restTemplate.setMessageConverters(messageConverters);
		this.resourceUriBuilder = resourceUriBuilder;
		this.searchArtistResourceUri = searchArtistResourceUri;
		this.restResultHandler = restResultHandler;
	}
	
	@Override
	protected RestResult<ArtistListEntity> doInBackground(String... params) {
		String url = resourceUriBuilder.setResourceUri(this.searchArtistResourceUri).build();
		String artistName = params[0];
		
		ArtistEntity searchArtistEntity = new ArtistEntity(null, artistName, null);
		
		String jsonResult = this.restTemplate.postForObject(url, searchArtistEntity, String.class);
		return restResultHandler.createRestResult(jsonResult, ArtistListEntity.class);
	}

}
