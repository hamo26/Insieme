package com.insieme.core.tracks.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.common.util.JSONUtil;
import com.insieme.core.guice.SelfInjectingServerResource;
import com.insieme.core.service.util.TrackValidator;
import com.insieme.core.track.service.TrackService;
import com.insieme.core.tracks.rest.handlers.RegisterTrackResource;
import com.insieme.core.tracks.rest.handlers.UpdateTrackResource;

/**
 * Implements {@link RegisterTrackResource}.
 */
public class UpdateTrackResourceImpl extends SelfInjectingServerResource implements UpdateTrackResource {
	
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
	}

	@Override
	@Put
	public String updateTrack(String trackRepresentation)
			throws InsiemeException {
		try {
			TrackEntity trackEntity = jsonUtil.deserializeRepresentation(trackRepresentation, TrackEntity.class);
			Collection<String> missingTrackFields = TrackValidator.getMissingTrackFields(trackEntity);
			if (!missingTrackFields.isEmpty()) {
				throw insiemeExceptionFactory.createInsiemeException("missing Fields: " + missingTrackFields.toString());
			} else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(
						InsiemePersistenceConstants.INSIEME_URL,
						InsiemePersistenceConstants.INSIEME_ROOT,
						InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);
				
				trackService.updateTrack(connection, trackEntity);
				return jsonUtil.serializeRepresentation(trackEntity);
			}
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExceptionFactory.createInsiemeException(e.getLocalizedMessage()).serializeJsonException();
		}
	}

	@Override
	@Post
	public String deleteTrack(String trackRepresentation)
			throws InsiemeException {
		// TODO Auto-generated method stub
		try {
			TrackEntity trackEntity = jsonUtil.deserializeRepresentation(trackRepresentation, TrackEntity.class);
			if (StringUtils.isEmpty(trackEntity.getTrackId())) {
				throw insiemeExceptionFactory.createInsiemeException("Missing Track Id");
			} else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(
						InsiemePersistenceConstants.INSIEME_URL,
						InsiemePersistenceConstants.INSIEME_ROOT,
						InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);
				
				trackService.deleteTrack(connection, trackEntity.getTrackId());
				return jsonUtil.serializeRepresentation(trackEntity);
			}
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExceptionFactory.createInsiemeException(e.getLocalizedMessage()).serializeJsonException();
		}
	}
	

}
