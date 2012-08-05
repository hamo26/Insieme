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
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.TrackListEntity;
import com.insieme.common.domain.rest.RestResult;
import com.insieme.common.domain.rest.RestResultHandler;

/**
 * Task to get tracks for a specific artist.
 */
public class GetTracksForArtist extends
		AsyncTask<ArtistEntity, Integer, RestResult<TrackListEntity>> {

	
	private final RestTemplate restTemplate;
	private final ResourceUriBuilder resourceUriBuilder;
	private final String artistTracksResourceUri;
	private final RestResultHandler restResultHandler;

	/**
	 * Default Constructor.
	 *
	 * @param restTemplate the rest template
	 * @param resourceUriBuilder the resource uri builder
	 * @param artistTracksResourceUri the artist tracks resource uri
	 * @param restResultHandler the rest result handler
	 */
	@SuppressWarnings("unchecked")
	@Inject
	public GetTracksForArtist(@Named("restTemplate") 
								final RestTemplate restTemplate,
								@Named("resourceUriBuilder") 
								final ResourceUriBuilder resourceUriBuilder,
								@Named("artistTracksResource")
								final String artistTracksResourceUri,
								@Named("restResultHandler")
								final RestResultHandler restResultHandler) {
		this.restTemplate = restTemplate;
		this.resourceUriBuilder = resourceUriBuilder;
		this.artistTracksResourceUri = artistTracksResourceUri;
		this.restResultHandler = restResultHandler;
		
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.addAll(Arrays.asList(new GsonHttpMessageConverter(), new StringHttpMessageConverter()));
		this.restTemplate.setMessageConverters(messageConverters);
		
	}
	
	@Override
	protected RestResult<TrackListEntity> doInBackground(ArtistEntity... params) {
		ArtistEntity artist = params[0];
		String url = resourceUriBuilder
				.setResourceUri(this.artistTracksResourceUri)
				.setId(artist.getArtistId())
				.build();
		
		String jsonResult = this.restTemplate.getForObject(url, String.class);
		return restResultHandler.createRestResult(jsonResult, TrackListEntity.class);
	}

}
