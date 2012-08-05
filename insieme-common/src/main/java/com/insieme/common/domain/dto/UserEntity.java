package com.insieme.common.domain.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * Simple user pojo that is structured to be serialized by JOOQ and automatically mapped.
 * Did not like the DTO generate by JOOQ so I chose to make my own.
 */
public class UserEntity extends RestEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5832677761522878554L;

	public static final String EMAIL_ADDRESS = "email-address";

	public static final String PASSWORD = "password";

	public static final String LAST_NAME = "last-name";

	public static final String FIRST_NAME = "first-name";

	public static final String USER_ID = "user-id";

	@SerializedName(USER_ID)
	private String userId;
	
	@SerializedName(FIRST_NAME)
	private String firstName;
	
	@SerializedName(LAST_NAME)
	private String lastName;
	
	@SerializedName(PASSWORD)
	private String password;
	
	@SerializedName(EMAIL_ADDRESS)
	private String emailAddress;
	
	public UserEntity(final String USER_ID, final String FIRST_NAME, 
				final String LAST_NAME, final String USER_PASSWORD, final String USER_EMAIL) {
		this.userId = USER_ID;
		this.firstName = FIRST_NAME;
		this.lastName = LAST_NAME;
		this.password = USER_PASSWORD;
		this.emailAddress = USER_EMAIL;
	}

	public String getUserId() {
		return this.userId;
	}
	
	public String getFirstName() {
		return this.firstName;
	}


	public String getLastName() {
		return this.lastName;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}
	
	public String getPassword() {
		return this.password;
	}
}
