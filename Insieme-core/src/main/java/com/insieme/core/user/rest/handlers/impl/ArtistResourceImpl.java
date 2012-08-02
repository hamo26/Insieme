package com.insieme.core.user.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.util.JSONUtil;
import com.insieme.core.artist.service.ArtistService;
import com.insieme.core.guice.SelfInjectingServerResource;
import com.insieme.core.service.util.ArtistValidator;
import com.insieme.core.user.rest.handlers.ArtistResource;

/**
 * Implements {@link ArtistResource}.
 */
public class ArtistResourceImpl extends SelfInjectingServerResource implements ArtistResource {

	private Connection connection;

	@Inject
	@Named("artistService")
	private ArtistService artistService;
	
	@Inject
	@Named("jsonUtil")
	private JSONUtil jsonUtil;
	
	@Inject
	private InsiemeExceptionFactory insiemeExceptionFactory;

	private String artistId;
	
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
		this.artistId = (String) getRequestAttributes().get("artistId");
    }

	@Override
	@Post
	public String registerArtist(String artistRepresentation) {
		try {
			ArtistEntity postedArtist = jsonUtil.deserializeRepresentation(
					artistRepresentation, ArtistEntity.class);
			Collection<String> missingFields = ArtistValidator.getMissingArtistFields(postedArtist);
			if (!missingFields.isEmpty()) {
				throw insiemeExceptionFactory
						.createInsiemeException("missing fields: " + missingFields.toString());
			} else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(
						InsiemePersistenceConstants.INSIEME_URL,
						InsiemePersistenceConstants.INSIEME_ROOT,
						InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);

				artistService.registerArtist(connection, postedArtist);
				
				return jsonUtil.serializeRepresentation(postedArtist);
			}
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExceptionFactory
					.createInsiemeException(e.getMessage())
					.serializeJsonException();
		}
	}

	@Override
	@Get
	public String getArtist() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(
					InsiemePersistenceConstants.INSIEME_URL,
					InsiemePersistenceConstants.INSIEME_ROOT,
					InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);
			ArtistEntity artistEntity = artistService.getArtist(connection, artistId);
			return jsonUtil.serializeRepresentation(artistEntity);
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExceptionFactory.createInsiemeException(
					e.getMessage()).serializeJsonException();
		}
	}

}
