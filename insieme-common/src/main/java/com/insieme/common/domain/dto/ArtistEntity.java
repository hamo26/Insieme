package com.insieme.common.domain.dto;

import com.google.gson.annotations.SerializedName;

public class ArtistEntity extends RestEntity {

	public static final String ARTIST_ID = "artist-id";
	
	public static final String GENRE = "genre";

	@SerializedName(ARTIST_ID)
	private final String artistId;
	
	@SerializedName(GENRE)
	private final String genre;
	
	public ArtistEntity(final String ARTIST_ID, final String GENRE) {
		artistId = ARTIST_ID;
		genre = GENRE;
	}

	public String getGenre() {
		return genre;
	}

	public String getArtistId() {
		return artistId;
	}
	
	
}
