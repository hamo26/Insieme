package com.insieme.android.track.task.impl;

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
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.common.domain.rest.RestResult;
import com.insieme.common.domain.rest.RestResultHandler;

/**
 * Task to register a track.
 */
public class RegisterTrackTask extends
		AsyncTask<TrackEntity, Integer, RestResult<TrackEntity>> {

	
	private final RestTemplate restTemplate;
	private final ResourceUriBuilder resourceUriBuilder;
	private final String registerTracksResourceUri;
	private final RestResultHandler restResultHandler;

	/**
	 * Default Constructor.
	 *
	 * @param restTemplate the rest template
	 * @param resourceUriBuilder the resource uri builder
	 * @param registerTracksResourceUri the artist tracks resource uri
	 * @param restResultHandler the rest result handler
	 */
	@SuppressWarnings("unchecked")
	@Inject
	public RegisterTrackTask(@Named("restTemplate") 
								final RestTemplate restTemplate,
								@Named("resourceUriBuilder") 
								final ResourceUriBuilder resourceUriBuilder,
								@Named("registerTrackResource")
								final String registerTracksResourceUri,
								@Named("restResultHandler")
								final RestResultHandler restResultHandler) {
		this.restTemplate = restTemplate;
		this.resourceUriBuilder = resourceUriBuilder;
		this.registerTracksResourceUri = registerTracksResourceUri;
		this.restResultHandler = restResultHandler;
		
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.addAll(Arrays.asList(new GsonHttpMessageConverter(), new StringHttpMessageConverter()));
		this.restTemplate.setMessageConverters(messageConverters);
		
	}
	
	@Override
	protected RestResult<TrackEntity> doInBackground(TrackEntity... params) {
		TrackEntity track = params[0];
		String url = resourceUriBuilder
				.setResourceUri(this.registerTracksResourceUri)
				.build();
		
		String jsonResult = this.restTemplate.postForObject(url, track, String.class);
		return restResultHandler.createRestResult(jsonResult, TrackEntity.class);
	}

}
