package com.insieme.core.login.domain.dto;

public class User {

	private String firstName;
	
	private String lastName;
	
	private String emailAddress;

	public String getFirstName() {
		return firstName;
	}

	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public User setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}
}
