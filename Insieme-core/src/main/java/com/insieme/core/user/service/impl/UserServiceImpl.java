package com.insieme.core.user.service.impl;

import java.sql.Connection;
import java.util.List;

import com.insieme.common.database.dto.InsiemeFactory;
import com.insieme.common.database.dto.Tables;
import com.insieme.core.login.domain.dto.User;
import com.insieme.core.user.service.UserService;
import com.insieme.database.exception.InsiemePersistenceException;


/**
 * Implementation of {@link UserService}.
 */
public class UserServiceImpl implements UserService {
	
	/**
	 * Default constructor.
	 */
	public UserServiceImpl() {
		//Empty constructor for now.
	}
	
	@Override
	public User getUser(Connection connection, String userId) throws InsiemePersistenceException{
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<User> userResult = createQuery.select()
											.from(Tables.USERS)
											.where(Tables.USERS.USER_ID.equal(userId))
											.fetchInto(User.class);
		if (userResult.size() > 1) {
			throw new InsiemePersistenceException("User id was not unique.");
		}
		return userResult.iterator().next();
	}

	@Override
	public User getUser(Connection connection, String userId, String encryptedPassword) throws InsiemePersistenceException {
		InsiemeFactory createQuery = new InsiemeFactory(connection);
		List<User> userResult = createQuery.select()
											.from(Tables.USERS)
											.where(Tables.USERS.USER_ID.equal(userId))
											.and(Tables.USERS.USER_PASSWORD.equal(encryptedPassword))
											.fetchInto(User.class);
		if (userResult.size() > 1) {
			throw new InsiemePersistenceException("User id was not unique.");
		}
		return userResult.iterator().next();
	}

}