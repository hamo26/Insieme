package com.insieme.android.user.task.impl;

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
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.domain.rest.RestResult;
import com.insieme.common.domain.rest.RestResultHandler;

public class LoginTask extends AsyncTask<String, Integer, RestResult<UserEntity>>{
	
	
	private final RestTemplate restTemplate;
	private final ResourceUriBuilder resourceUriBuilder;
	private final String authenticationResourceUri;
	private final RestResultHandler restResultHandler;
	
	@SuppressWarnings("unchecked")
	@Inject
	public LoginTask(@Named("restTemplate") 
							final RestTemplate restTemplate,
							@Named("resourceUriBuilder") 
							final ResourceUriBuilder resourceUriBuilder,
							@Named("authenticationResource")
							final String authenticationResourceUri,
							@Named("restResultHandler")
							final RestResultHandler restResultHandler) {
		super();
		this.restTemplate = restTemplate;
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.addAll(Arrays.asList(new GsonHttpMessageConverter(), new StringHttpMessageConverter()));

		this.restTemplate.setMessageConverters(messageConverters);
		this.resourceUriBuilder = resourceUriBuilder;
		this.authenticationResourceUri = authenticationResourceUri;
		this.restResultHandler = restResultHandler;
	}

	@Override
	protected RestResult<UserEntity> doInBackground(String... params) {
		String url = resourceUriBuilder.setResourceUri(this.authenticationResourceUri).build();
		String userId = params[0];
		String password = params[1];
		
		UserEntity postUser = new UserEntity(userId, null, null, password,null);
		
		String jsonResult = this.restTemplate.postForObject(url, postUser, String.class);
		return restResultHandler.createRestResult(jsonResult, UserEntity.class);
	}
	
	
}
