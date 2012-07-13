package com.insieme.core.login.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.guice.SelfInjectingServerResource;
import com.insieme.core.login.domain.dto.User;
import com.insieme.core.login.rest.handlers.LoginResource;
import com.insieme.core.user.service.UserService;


/**
 * Login Resource to login and register a user. 
 * 
 * TODO: consider separating resources into one for loging in and one for registering and deleting accounts.
 */
public class LoginResourceImpl extends SelfInjectingServerResource implements LoginResource {
	
	private String userId;
	private Connection connection;
	
	@Inject
	@Named("userService")
	private UserService userService;
	
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
        this.userId =  (String) getRequestAttributes().get("userId");
    }

	public String retrieve() {
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(InsiemePersistenceConstants.INSIEME_URL, 
            		InsiemePersistenceConstants.INSIEME_ROOT, InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);
            
            User user = userService.getUser(connection, userId);
            return user.getEmailAddress()+" "+user.getFirstName()+" "+user.getLastName();
		} catch (Exception e) {
			return e.getMessage();
		}
            
	}

	public String getUserName(String userJson) {
		return null;
	}

	public void remove() {
		
	}

	
	
}

