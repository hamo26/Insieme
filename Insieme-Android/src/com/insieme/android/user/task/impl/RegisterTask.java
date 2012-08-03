package com.insieme.android.user.task.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.android.util.ResourceUriBuilder;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.domain.rest.RestResult;
import com.insieme.common.domain.rest.RestResultHandler;

import android.os.AsyncTask;

public class RegisterTask extends AsyncTask<String, Integer, RestResult<UserEntity>> {

	private final RestTemplate restTemplate;
	private final ResourceUriBuilder resourceUriBuilder;
	private final String registrationResourceUri;
	private final RestResultHandler restResultHandler;
	
	@SuppressWarnings("unchecked")
	@Inject
	public RegisterTask(@Named("restTemplate") 
							final RestTemplate restTemplate,
							@Named("resourceUriBuilder") 
							final ResourceUriBuilder resourceUriBuilder,
							@Named("registrationResource")
							final String registrationResourceUri,
							@Named("restResultHandler")
							final RestResultHandler restResultHandler) {
		super();
		this.restTemplate = restTemplate;
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.addAll(Arrays.asList(new GsonHttpMessageConverter(), new StringHttpMessageConverter()));

		this.restTemplate.setMessageConverters(messageConverters);
		this.resourceUriBuilder = resourceUriBuilder;
		this.registrationResourceUri = registrationResourceUri;
		this.restResultHandler = restResultHandler;
	}
	
	@Override
	protected RestResult<UserEntity> doInBackground(String... params) {
		String url = resourceUriBuilder.setResourceUri(this.registrationResourceUri).build();
		String userId = params[0];
		String password = params[1];
		String firstName = params[2];
		String lastName = params[3];
		String emailAddress = params[4];
		
		UserEntity postUser = new UserEntity(userId, firstName, lastName, password, emailAddress);
		
		String jsonResult = this.restTemplate.postForObject(url, postUser, String.class);
		return restResultHandler.createRestResult(jsonResult, UserEntity.class);
	}
	

}
