package com.insieme.core.track.service;

import java.sql.Connection;

import com.google.inject.ImplementedBy;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.core.track.service.impl.TrackServiceImpl;

@ImplementedBy(TrackServiceImpl.class)
public interface TrackService {

	/**
	 * Gets a track by a specific artist.
	 *
	 * @param connection the connection
	 * @param trackId the track id
	 * @return the track
	 * @throws InsiemeException the insieme exception
	 */
	public TrackEntity getTrack(Connection connection, String trackId) throws InsiemeException; 
	
	/**
	 * Registers a track by a specific artist.
	 *
	 * @param connection the connection
	 * @param trackEntity the track entity
	 * @throws InsiemeException the insieme exception
	 */
	public void registerTrack(Connection connection, TrackEntity trackEntity) throws InsiemeException;
}
