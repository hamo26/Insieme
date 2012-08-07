package com.insieme.common.domain.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 * List of artists representation.
 */
public class ArtistListEntity extends RestEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6693797182549014092L;
	private final Collection<ArtistEntity> artists;
	
	/**
	 * Default constructor.
	 *
	 * @param artists the artists
	 */
	public ArtistListEntity(final Collection<ArtistEntity> artists) {
		this.artists = artists;
	}

	/**
	 * Gets the artists.
	 *
	 * @return the artists
	 */
	public Collection<ArtistEntity> getArtists() {
		return artists;
	}

}
