package com.insieme.common.domain.dto;

import java.util.Collection;

/**
 * List of artists representation.
 */
public class ArtistListEntity extends RestEntity {
	
	private final Collection<ArtistEntity> artists;
	
	public ArtistListEntity(final Collection<ArtistEntity> artists) {
		this.artists = artists;
	}

	public Collection<ArtistEntity> getArtists() {
		return artists;
	}

}
