/**
 * This class is generated by jOOQ
 */
package com.insieme.common.database;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;

import com.insieme.common.database.tables.Artists;
import com.insieme.common.database.tables.Tracks;
import com.insieme.common.database.tables.Users;
import com.insieme.common.database.tables.records.ArtistsRecord;
import com.insieme.common.database.tables.records.TracksRecord;
import com.insieme.common.database.tables.records.UsersRecord;


/**
 * Class that jooq uses to keep track of database keys.
 */
public class Keys {

	// IDENTITY definitions

	// UNIQUE and PRIMARY KEY definitions
	public static final UniqueKey<ArtistsRecord> KEY_ARTISTS_PRIMARY = UniqueKeys0.KEY_ARTISTS_PRIMARY;
	public static final UniqueKey<UsersRecord> KEY_USERS_PRIMARY = UniqueKeys0.KEY_USERS_PRIMARY;
	public static final UniqueKey<UsersRecord> KEY_USERS_USER_PASSWORD_UNIQUE = UniqueKeys0.KEY_USERS_USER_PASSWORD_UNIQUE;
	public static final UniqueKey<UsersRecord> KEY_USERS_USER_EMAIL_UNIQUE = UniqueKeys0.KEY_USERS_USER_EMAIL_UNIQUE;

	// FOREIGN KEY definitions
	public static final ForeignKey<ArtistsRecord, UsersRecord> USER_ID_KEY = ForeignKeys0.USER_ID_KEY;
	public static final ForeignKey<TracksRecord, ArtistsRecord> ARTIST_ID = ForeignKeys0.ARTIST_ID;

	/**
	 * No instances
	 */
	private Keys() {}

	@SuppressWarnings("unchecked")
	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<ArtistsRecord> KEY_ARTISTS_PRIMARY = createUniqueKey(Artists.ARTISTS, Artists.ARTISTS.ARTIST_ID);
		public static final UniqueKey<UsersRecord> KEY_USERS_PRIMARY = createUniqueKey(Users.USERS, Users.USERS.USER_ID);
		public static final UniqueKey<UsersRecord> KEY_USERS_USER_PASSWORD_UNIQUE = createUniqueKey(Users.USERS, Users.USERS.USER_PASSWORD);
		public static final UniqueKey<UsersRecord> KEY_USERS_USER_EMAIL_UNIQUE = createUniqueKey(Users.USERS, Users.USERS.USER_EMAIL);
	}
	
	@SuppressWarnings("unchecked")
	private static class ForeignKeys0 extends org.jooq.impl.AbstractKeys {
		public static final ForeignKey<ArtistsRecord, UsersRecord> USER_ID_KEY = createForeignKey(Keys.KEY_USERS_PRIMARY, Artists.ARTISTS, Artists.ARTISTS.ARTIST_ID);
		public static final ForeignKey<TracksRecord, ArtistsRecord> ARTIST_ID = createForeignKey(Keys.KEY_ARTISTS_PRIMARY, Tracks.TRACKS, Tracks.TRACKS.ARTIST_ID);
	}
}