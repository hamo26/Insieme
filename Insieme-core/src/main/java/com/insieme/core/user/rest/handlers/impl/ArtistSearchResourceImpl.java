package com.insieme.core.user.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.ArtistListEntity;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.util.JSONUtil;
import com.insieme.core.artist.service.ArtistService;
import com.insieme.core.guice.SelfInjectingServerResource;
import com.insieme.core.user.rest.handlers.ArtistSearchResource;

/**
 * Implementation of {@link ArtistSearchResource}.
 */
public class ArtistSearchResourceImpl extends SelfInjectingServerResource
		implements ArtistSearchResource {

	private Connection connection;

	@Inject
	@Named("artistService")
	private ArtistService artistService;
	
	@Inject
	@Named("jsonUtil")
	private JSONUtil jsonUtil;
	
	@Inject
	private InsiemeExceptionFactory insiemeExceptionFactory;
	
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}
	
	@Override
	@Post
	public String getArtists(String artistSearchRepresentation) {
		try {
			ArtistEntity postedArtist = jsonUtil.deserializeRepresentation(
					artistSearchRepresentation, ArtistEntity.class);
			if (StringUtils.isEmpty(postedArtist.getName())) {
				throw insiemeExceptionFactory
						.createInsiemeException("missing fields search terms");
			} else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(
						InsiemePersistenceConstants.INSIEME_URL,
						InsiemePersistenceConstants.INSIEME_ROOT,
						InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);

				Collection<ArtistEntity> result = artistService.searchForArtists(connection, postedArtist);
				
				ArtistListEntity artistListEntity = new ArtistListEntity(result);
				
				return jsonUtil.serializeRepresentation(artistListEntity);
			}
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExceptionFactory
					.createInsiemeException(e.getMessage())
					.serializeJsonException();
		}
	}

}
