package com.insieme.core.user.rest.handlers.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.database.InsiemePersistenceConstants;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.util.JSONUtil;
import com.insieme.core.guice.SelfInjectingServerResource;
import com.insieme.core.service.util.UserValidator;
import com.insieme.core.user.rest.handlers.RegistrationResource;
import com.insieme.core.user.service.UserService;

/**
 * Implementation of {@link RegistrationResource}.
 */
public class RegistrationResourceImpl extends SelfInjectingServerResource
		implements RegistrationResource {

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
	@Post
	public String registerUser(String userRepresentation) {
		try {
			UserEntity postedUser = jsonUtil.deserializeRepresentation(
					userRepresentation, UserEntity.class);
			Collection<String> missingFields = UserValidator.getMissingUserFields(
					postedUser, false);
			if (!missingFields.isEmpty()) {
				throw insiemeExcptionFactory
						.createInsiemeException("missing fields: " + missingFields.toString());
			} else {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(
						InsiemePersistenceConstants.INSIEME_URL,
						InsiemePersistenceConstants.INSIEME_ROOT,
						InsiemePersistenceConstants.INSIEME_ROOT_PASSWORD);

				userService.registerUser(connection, postedUser);
				
				return jsonUtil.serializeRepresentation(postedUser);
			}
		} catch (InsiemeException e) {
			return e.serializeJsonException();
		} catch (Exception e) {
			return insiemeExcptionFactory
					.createInsiemeException(e.getMessage())
					.serializeJsonException();
		}
	}

}
