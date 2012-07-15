package com.insieme.core.user.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.guice.SelfInjectingServerResource;
import com.insieme.common.util.json.JSONUtil;
import com.insieme.core.domain.dto.InsiemeException;
import com.insieme.core.domain.dto.User;
import com.insieme.core.user.rest.handlers.AuthenticationResource;
import com.insieme.core.user.service.UserService;
import com.mysql.jdbc.StringUtils;


/**
 * Login Resource to login and register a user. 
 * 
 * TODO: consider separating resources into one for loging in and one for registering and deleting accounts.
 */
public class AuthenticationResourceImpl extends SelfInjectingServerResource implements AuthenticationResource {
	
	private Connection connection;
	
	@Inject
	@Named("userService")
	private UserService userService;
	
	@Inject
	@Named("jsonUtil")
	private JSONUtil jsonUtil;
	
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
    }

	@Override
	public String authenticateUser(String userRepresentation) {
		try {
			User postedUser = jsonUtil.deserializeRepresentation(userRepresentation, User.class);
			if (StringUtils.isNullOrEmpty(postedUser.getUserId()) || StringUtils.isNullOrEmpty(postedUser.getPassword())) {
				throw new InsiemeException("field userId or password is empty");
			} else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(InsiemePersistenceConstants.INSIEME_URL, 
						InsiemePersistenceConstants.INSIEME_ROOT, InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);
				
				User user = userService.getUser(connection, postedUser.getUserId(), postedUser.getPassword());
				return jsonUtil.serializeRepresentation(user);			
			}
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return new InsiemeException(e.getMessage()).serializeJsonException();
		}
	}
}

