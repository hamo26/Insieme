package com.insieme.core.user.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.util.JSONUtil;
import com.insieme.core.guice.SelfInjectingServerResource;
import com.insieme.core.user.rest.handlers.AuthenticationResource;
import com.insieme.core.user.service.UserService;
import com.mysql.jdbc.StringUtils;


/**
 * Implementation of {@link AuthenticationResource}.
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
	
	@Inject
	private InsiemeExceptionFactory insiemeExcptionFactory;
	
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
    }

	@Override
	public String authenticateUser(String userRepresentation) {
		try {
			UserEntity postedUser = jsonUtil.deserializeRepresentation(userRepresentation, UserEntity.class);
			if (StringUtils.isNullOrEmpty(postedUser.getUserId()) || StringUtils.isNullOrEmpty(postedUser.getPassword())) {
				throw insiemeExcptionFactory.createInsiemeException("field userId or password is empty");
			} else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(InsiemePersistenceConstants.INSIEME_URL, 
						InsiemePersistenceConstants.INSIEME_ROOT, InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);
				
				UserEntity user = userService.getUser(connection, postedUser.getUserId(), postedUser.getPassword());
				return jsonUtil.serializeRepresentation(user);			
			}
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExcptionFactory.createInsiemeException(e.getMessage()).serializeJsonException();
		}
	}
}

