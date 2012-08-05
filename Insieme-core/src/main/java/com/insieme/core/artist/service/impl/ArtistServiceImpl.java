package com.insieme.core.artist.service.impl;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.insieme.common.database.InsiemeFactory;
import com.insieme.common.database.Tables;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.core.artist.service.ArtistService;

/**
 * Implements {@link ArtistService}.
 */
public class ArtistServiceImpl implements ArtistService {

	private final InsiemeExceptionFactory insiemeExceptionFactory;

	/**
	 * Default constructor.
	 *
	 * @param insiemeExceptionFactory the insieme exception factory
	 */
	@Inject
	public ArtistServiceImpl(final InsiemeExceptionFactory insiemeExceptionFactory) {
		this.insiemeExceptionFactory = insiemeExceptionFactory;
	}
	
	@Override
	public void registerArtist(Connection connection, ArtistEntity artistEntity) throws InsiemeException {
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<ArtistEntity> artistsResult = createQuery.select()
											.from(Tables.ARTISTS)
											.where(Tables.ARTISTS.ARTIST_ID.equal(artistEntity.getArtistId()))
											.fetchInto(ArtistEntity.class);
		if (!artistsResult.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("Artist id: " + artistEntity.getArtistId() + " not unique");
		} else {
			createQuery.insertInto(Tables.ARTISTS, Tables.ARTISTS.ARTIST_ID, Tables.ARTISTS.ARTIST_NAME, Tables.ARTISTS.GENRE)
												   .values(artistEntity.getArtistId(), artistEntity.getName(), artistEntity.getGenre()).execute();
		}
		
	}

	@Override
	public ArtistEntity getArtist(Connection connection, String artistId) throws InsiemeException {
		InsiemeFactory retrieveQuery = new InsiemeFactory(connection);
		List<ArtistEntity> artistsResult = retrieveQuery.select()
				.from(Tables.ARTISTS)
				.where(Tables.ARTISTS.ARTIST_ID.equal(artistId))
				.fetchInto(ArtistEntity.class);
		if(artistsResult.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("Artist with id:" + artistId + " not found.");
		}
		return artistsResult.get(0);
	}

	@Override
	public Collection<ArtistEntity> searchForArtists(Connection connection,
			ArtistEntity artistEntity) throws InsiemeException {
		InsiemeFactory retrieveQuery = new InsiemeFactory(connection);
		List<ArtistEntity> artistsResult = retrieveQuery.select()
				.from(Tables.ARTISTS)
				.where(Tables.ARTISTS.ARTIST_NAME.like(artistEntity.getName()))
				.fetchInto(ArtistEntity.class);
		return artistsResult;
	}

	@Override
	public Collection<TrackEntity> getTracksForArtist(Connection connection,
			String artistId) throws InsiemeException {
		InsiemeFactory retrieveQuery = new InsiemeFactory(connection);
		List<TrackEntity> tracksResult = retrieveQuery.select()
				.from(Tables.TRACKS)
				.where(Tables.TRACKS.ARTIST_ID.equal(artistId))
				.fetchInto(TrackEntity.class);
		return tracksResult;
	}
	
	
}
