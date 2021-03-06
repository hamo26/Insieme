/**
 * This class is generated by jOOQ
 */
package com.insieme.common.database.tables.records;

import java.util.List;

import org.jooq.impl.TableRecordImpl;

import com.insieme.common.database.tables.Artists;
import com.insieme.common.database.tables.Tracks;
import com.insieme.common.database.tables.Users;


/**
 * Artists Record class used by JOOQ.
 */
public class ArtistsRecord extends TableRecordImpl<ArtistsRecord> {

	private static final long serialVersionUID = -280478613;

	/**
	 * Foreign key for user id. An artist is a user.
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 * <p>
	 * This column is part of a FOREIGN KEY: <code><pre>
	 * CONSTRAINT USER_ID_KEY
	 * FOREIGN KEY (ARTIST_ID)
	 * REFERENCES insieme.users (USER_ID)
	 * </pre></code>
	 */
	public void setArtistId(String value) {
		setValue(Artists.ARTISTS.ARTIST_ID, value);
	}

	/**
	 * Foreign key for user id. An artist is a user.
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 * <p>
	 * This column is part of a FOREIGN KEY: <code><pre>
	 * CONSTRAINT USER_ID_KEY
	 * FOREIGN KEY (ARTIST_ID)
	 * REFERENCES insieme.users (USER_ID)
	 * </pre></code>
	 */
	public String getArtistId() {
		return getValue(Artists.ARTISTS.ARTIST_ID);
	}

	/**
	 * Foreign key for user id. An artist is a user.
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 * <p>
	 * This column is part of a FOREIGN KEY: <code><pre>
	 * CONSTRAINT USER_ID_KEY
	 * FOREIGN KEY (ARTIST_ID)
	 * REFERENCES insieme.users (USER_ID)
	 * </pre></code>
	 */
	public List<TracksRecord> fetchTracksList() {
		return create()
			.selectFrom(Tracks.TRACKS)
			.where(Tracks.TRACKS.ARTIST_ID.equal(getValue(Artists.ARTISTS.ARTIST_ID)))
			.fetch();
	}

	/**
	 * Link this record to a given {@link com.insieme.common.database.tables.records.UsersRecord 
	 * UsersRecord}
	 */
	public void setArtistId(UsersRecord value) {
		if (value == null) {
			setValue(Artists.ARTISTS.ARTIST_ID, null);
		}
		else {
			setValue(Artists.ARTISTS.ARTIST_ID, value.getValue(Users.USERS.USER_ID));
		}
	}

	/**
	 * Foreign key for user id. An artist is a user.
	 * <p>
	 * This column is part of the table's PRIMARY KEY
	 * <p>
	 * This column is part of a FOREIGN KEY: <code><pre>
	 * CONSTRAINT USER_ID_KEY
	 * FOREIGN KEY (ARTIST_ID)
	 * REFERENCES insieme.users (USER_ID)
	 * </pre></code>
	 */
	public UsersRecord fetchUsers() {
		return create()
			.selectFrom(Users.USERS)
			.where(Users.USERS.USER_ID.equal(getValue(Artists.ARTISTS.ARTIST_ID)))
			.fetchOne();
	}

	/**
	 * The table column <code>insieme.artists.GENRE</code>
	 */
	public void setGenre(String value) {
		setValue(Artists.ARTISTS.GENRE, value);
	}

	/**
	 * The table column <code>insieme.artists.GENRE</code>
	 */
	public String getGenre() {
		return getValue(Artists.ARTISTS.GENRE);
	}

	/**
	 * Create a detached ArtistsRecord
	 */
	public ArtistsRecord() {
		super(Artists.ARTISTS);
	}
}
