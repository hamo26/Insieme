package com.insieme.core.track.service;

import java.sql.Connection;

import com.google.inject.ImplementedBy;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.core.track.service.impl.TrackServiceImpl;

/**
 *  Peristence track service used by resources to get tracks.
 */
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

	/**
	 * Update a track by a specific artist..
	 *
	 * @param connection the connection
	 * @param trackEntity the track entity
	 */
	public void updateTrack(Connection connection, TrackEntity trackEntity) throws InsiemeException;
	
	/**
	 * Delete track associated with an artist.
	 *
	 * @param connection the connection
	 * @param trackId the track id
	 * @throws InsiemeException the insieme exception
	 */
	public void deleteTrack(Connection connection, String trackId) throws InsiemeException;
}
