package com.insieme.core.artist.service;

import java.sql.Connection;

import com.google.inject.ImplementedBy;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.core.user.rest.handlers.impl.ArtistResourceImpl;

/**
 * Peristence artist service used by resources to get artists. 
 */
@ImplementedBy(ArtistResourceImpl.class)
public interface ArtistService {
	
	/**
	 * Gets an artist.
	 *
	 * @param artistId the artist id
	 * @return the artist
	 */
	public ArtistEntity getArtist(Connection connection, String artistId) throws InsiemeException;
	
	/**
	 * Registers an artist.
	 *
	 * @param connection the connection
	 * @param artistEntity the artist entity
	 */
	public void registerArtist(Connection connection, ArtistEntity artistEntity) throws InsiemeException;

}
