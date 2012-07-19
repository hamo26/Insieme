package com.insieme.core.user.service;

import java.sql.Connection;

import com.google.inject.ImplementedBy;
import com.insieme.common.database.transactions.Transaction;
import com.insieme.common.domain.dto.InsiemeException;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.core.user.service.impl.UserServiceImpl;

/**
 * Peristence user service used by resources to get users.
 */
@ImplementedBy(UserServiceImpl.class)
public interface UserService {
	
	/**
	 * Gets a user based on userId.
	 *
	 * @param connection the sql connection.
	 * @param userId the user id
	 * @return the user
	 */
	@Transaction
	public UserEntity getUser(Connection connection, String userId) throws InsiemeException;
	
	/**
	 * Checks if a user is verified. Enforces that userid and password tuple
	 * exist in record before returning User. This is a way of verifying a user for login.
	 * 
	 * TO DO: consider having a login service that provides an authCookie as well.
	 *
	 * @param userId the user id
	 * @param password the password
	 * @param connection the sql connection
	 * @return true, if is user verified
	 */
	@Transaction
	public UserEntity getUser(Connection connection, String userId, String password) throws InsiemeException;
}
