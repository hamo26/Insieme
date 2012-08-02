package com.insieme.core.track.service.impl;

import java.sql.Connection;
import java.util.List;

import com.google.inject.Inject;
import com.insieme.common.database.InsiemeFactory;
import com.insieme.common.database.Tables;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.core.track.service.TrackService;

/**
 * Implements {@link TrackService}.
 */
public class TrackServiceImpl implements TrackService {

	private final InsiemeExceptionFactory insiemeExceptionFactory;

	@Inject
	public TrackServiceImpl(final InsiemeExceptionFactory insiemeExceptionFactory) {
		this.insiemeExceptionFactory = insiemeExceptionFactory;
	}

	@Override
	public TrackEntity getTrack(Connection connection, String trackId) throws InsiemeException {
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<TrackEntity> result = createQuery
				.select()
				.from(Tables.TRACKS)
				.where(Tables.TRACKS.TRACK_ID.equal(trackId))
				.fetchInto(TrackEntity.class);
		if(result.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("Track with id:" + trackId + " not found.");
		}
		return result.get(0);
	}

	@Override
	public void registerTrack(Connection connection, TrackEntity trackEntity)
			throws InsiemeException {
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<TrackEntity> result = createQuery.select()
				.from(Tables.TRACKS)
				.where(Tables.TRACKS.TRACK_ID.equal(trackEntity.getTrackId()))
				.fetchInto(TrackEntity.class);
		if (!result.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("Track id was not unique.");
		} else {
			createQuery.insertInto(Tables.TRACKS, Tables.TRACKS.ARTIST_ID, Tables.TRACKS.DESCRIPTION, 
					Tables.TRACKS.DOWNLOAD_COUNT, Tables.TRACKS.GENRE, Tables.TRACKS.TRACK_ID,
					Tables.TRACKS.TRACK_NAME)
				   .values(trackEntity.getArtistId(), 
						   trackEntity.getDescription(), trackEntity.getDownloadCount(), 
						   trackEntity.getGenre(), 
						   trackEntity.getTrackId(), trackEntity.getName()).execute();
		}
	}

	@Override
	public void updateTrack(Connection connection, TrackEntity trackEntity) throws InsiemeException {
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<TrackEntity> result = createQuery.select()
				.from(Tables.TRACKS)
				.where(Tables.TRACKS.TRACK_ID.equal(trackEntity.getTrackId()))
				.fetchInto(TrackEntity.class);
		if (result.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("Track with id: " + trackEntity.getTrackId() + " does not exist");
		} else {
			createQuery.update(Tables.TRACKS)
			.set(Tables.TRACKS.DESCRIPTION, trackEntity.getDescription())
			.set(Tables.TRACKS.DOWNLOAD_COUNT, trackEntity.getDownloadCount())
			.set(Tables.TRACKS.GENRE, trackEntity.getGenre())
			.set(Tables.TRACKS.TRACK_NAME, trackEntity.getTrackId())
			.where(Tables.TRACKS.TRACK_ID.equal(trackEntity.getTrackId()))
			.execute();
		}
	}
}
