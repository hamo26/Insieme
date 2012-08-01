package com.insieme.core.tracks.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.common.util.JSONUtil;
import com.insieme.core.guice.SelfInjectingServerResource;
import com.insieme.core.track.service.TrackService;
import com.insieme.core.tracks.rest.handlers.TracksResource;

/**
 * Implementation of {@link TracksResource}.
 */
public class TracksResourceImpl extends SelfInjectingServerResource implements
		TracksResource {

	private String trackId;
	private Connection connection;
	
	@Inject
	@Named("jsonUtil")
	private JSONUtil jsonUtil;

	@Inject
	private InsiemeExceptionFactory insiemeExceptionFactory;

	@Inject
	@Named("trackService")
	private TrackService trackService;

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		this.trackId = (String) getRequestAttributes().get("trackId");
	}

	public String getTrack() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(
					InsiemePersistenceConstants.INSIEME_URL,
					InsiemePersistenceConstants.INSIEME_ROOT,
					InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);
			TrackEntity track = trackService.getTrack(connection, trackId);
			return jsonUtil.serializeRepresentation(track);
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExceptionFactory.createInsiemeException(
					e.getMessage()).serializeJsonException();
		}
	}
}
