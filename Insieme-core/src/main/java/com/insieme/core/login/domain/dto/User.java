package com.insieme.core.login.domain.dto;

/**
 * Simple user pojo that is structured to be serialized by JOOQ and automatically mapped.
 * Did not like the DTO generate by JOOQ so I chose to make my own.
 */
public class User {
	
	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private String emailAddress;
	
	public User(final String USER_ID, final String FIRST_NAME, 
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
