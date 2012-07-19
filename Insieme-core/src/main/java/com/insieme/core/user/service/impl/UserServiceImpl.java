package com.insieme.core.user.service.impl;

import java.sql.Connection;
import java.util.List;

import com.google.inject.Inject;
import com.insieme.common.database.dto.InsiemeFactory;
import com.insieme.common.database.dto.Tables;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.InsiemeExceptionFactory;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.core.user.service.UserService;


/**
 * Implementation of {@link UserService}.
 */
public class UserServiceImpl implements UserService {
	
	private final InsiemeExceptionFactory insiemeExceptionFactory;

	/**
	 * Default constructor.
	 *
	 * @param insiemeExceptionFactory the insieme exception factory
	 */
	@Inject
	public UserServiceImpl(final InsiemeExceptionFactory insiemeExceptionFactory) {
		this.insiemeExceptionFactory = insiemeExceptionFactory;
	}
	
	@Override
	public UserEntity getUser(Connection connection, String userId) throws InsiemeException{
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<UserEntity> userResult = createQuery.select()
											.from(Tables.USERS)
											.where(Tables.USERS.USER_ID.equal(userId))
											.fetchInto(UserEntity.class);
		if (userResult.size() > 1) {
			throw insiemeExceptionFactory.createInsiemeException("User id was not unique.");
		} else if(userResult.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("User with user-id:" + userId + " does not exist");
		}
		return userResult.iterator().next();
	}

	@Override
	public UserEntity getUser(Connection connection, String userId, String encryptedPassword) throws InsiemeException {
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<UserEntity> userResult = createQuery.select()
											.from(Tables.USERS)
											.where(Tables.USERS.USER_ID.equal(userId))
											.and(Tables.USERS.USER_PASSWORD.equal(encryptedPassword))
											.fetchInto(UserEntity.class);
		if (userResult.size() > 1) {
			throw insiemeExceptionFactory.createInsiemeException("User id was not unique.");
		} else if(userResult.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("User with id:" + userId + " not found.");
		}
		return userResult.iterator().next();
	}

}
