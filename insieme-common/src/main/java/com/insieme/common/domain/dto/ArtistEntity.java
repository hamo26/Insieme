package com.insieme.common.domain.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * Artist Entity DTO used to serialize/deserialize artist representations.
 */
public class ArtistEntity extends RestEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8458935910072046853L;

	public static final String ARTIST_ID = "artist-id";
	
	public static final String GENRE = "genre";
	
	public static final String ARTIST_NAME = "artist-name";

	@SerializedName(ARTIST_ID)
	private final String artistId;
	
	@SerializedName(GENRE)
	private final String genre;
	
	@SerializedName(ARTIST_NAME)
	private final String name;
	
	/**
	 * Default Constructor. 
	 * 
	 * JOOQ also uses the constructor to read into/from database.
	 *
	 * @param ARTIST_ID the artist id
	 * @param ARTIST_NAME the artist name
	 * @param GENRE the genre
	 */
	public ArtistEntity(final String ARTIST_ID, final String ARTIST_NAME,
			final String GENRE) {
		artistId = ARTIST_ID;
		genre = GENRE;
		name = ARTIST_NAME;
	}

	/**
	 * Gets the genre.
	 *
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Gets the artist id.
	 *
	 * @return the artist id
	 */
	public String getArtistId() {
		return artistId;
	}

	public String getName() {
		return name;
	}
}
