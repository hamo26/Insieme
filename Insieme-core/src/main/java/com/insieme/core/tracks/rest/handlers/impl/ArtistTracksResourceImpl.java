package com.insieme.core.tracks.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.common.domain.dto.TrackListEntity;
import com.insieme.common.util.JSONUtil;
import com.insieme.core.artist.service.ArtistService;
import com.insieme.core.guice.SelfInjectingServerResource;
import com.insieme.core.tracks.rest.handlers.ArtistTracksResource;

/**
 * Implementation of {@link ArtistTracksResource}.
 */
public class ArtistTracksResourceImpl extends SelfInjectingServerResource
		implements ArtistTracksResource {

	private String artistId;
	private Connection connection;
	
	@Inject
	@Named("jsonUtil")
	private JSONUtil jsonUtil;

	@Inject
	private InsiemeExceptionFactory insiemeExceptionFactory;

	@Inject
	@Named("artistService")
	private ArtistService artistService;

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		this.artistId = (String) getRequestAttributes().get("artistId");
	}
	
	@Override
	@Get
	public String getTracksForArtist() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(
					InsiemePersistenceConstants.INSIEME_URL,
					InsiemePersistenceConstants.INSIEME_ROOT,
					InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);
			Collection<TrackEntity> tracks = artistService.getTracksForArtist(connection, artistId);
			return jsonUtil.serializeRepresentation(new TrackListEntity(tracks));
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExceptionFactory.createInsiemeException(
					e.getMessage()).serializeJsonException();
		}
	}

}
