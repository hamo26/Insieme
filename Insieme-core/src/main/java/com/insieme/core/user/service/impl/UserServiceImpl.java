package com.insieme.core.user.service.impl;

import java.sql.Connection;
import java.util.List;

import com.google.inject.Inject;
import com.insieme.common.database.InsiemeFactory;
import com.insieme.common.database.Tables;
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
	public void registerUser(Connection connection, UserEntity userEntity) throws InsiemeException{
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<UserEntity> userResult = createQuery.select()
											.from(Tables.USERS)
											.where(Tables.USERS.USER_ID.equal(userEntity.getUserId()))
											.or(Tables.USERS.USER_EMAIL.equal(userEntity.getEmailAddress()))
											.or(Tables.USERS.USER_PASSWORD.equal(userEntity.getPassword()))
											.fetchInto(UserEntity.class);
		if (!userResult.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("User id or email address was not unique.");
		} else {
			createQuery.insertInto(Tables.USERS, Tables.USERS.USER_ID, Tables.USERS.FIRST_NAME, 
													Tables.USERS.LAST_NAME, Tables.USERS.USER_PASSWORD, Tables.USERS.USER_EMAIL)
												   .values(userEntity.getUserId(), 
																				userEntity.getFirstName(), userEntity.getLastName(), userEntity.getPassword(), 
																				userEntity.getEmailAddress()).execute();
		}
	}

	@Override
	public UserEntity getUser(Connection connection, String userId, String encryptedPassword) throws InsiemeException {
		InsiemeFactory retrieveQuery = new InsiemeFactory(connection);
		List<UserEntity> userResult = retrieveQuery.select()
											.from(Tables.USERS)
											.where(Tables.USERS.USER_ID.equal(userId))
											.and(Tables.USERS.USER_PASSWORD.equal(encryptedPassword))
											.fetchInto(UserEntity.class);
		if(userResult.isEmpty()) {
			throw insiemeExceptionFactory.createInsiemeException("User with id:" + userId + " not found.");
		}
		return userResult.iterator().next();
	}

}
