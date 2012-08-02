package com.insieme.core.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.insieme.common.domain.dto.UserEntity;
import com.mysql.jdbc.StringUtils;

/**
 * Convenience utility to validate user fields. 
 * For now, only checks for empty fields.
 */
public class UserValidator {

	@SuppressWarnings("serial")
	public static Collection<String> getMissingUserFields(final UserEntity user, final Boolean isLogin) {
		Collection<String> missingLoginFields = new ArrayList<String>();
		Collection<String> missingRegistrationFields = new ArrayList<String>();
		Collection<String> loginFields = Arrays.asList(UserEntity.USER_ID,
				UserEntity.PASSWORD);

		Map<String, String> userPropertiesMap = new LinkedHashMap<String, String>() {
			{
				put(UserEntity.USER_ID, user.getUserId());
				put(UserEntity.FIRST_NAME, user.getFirstName());
				put(UserEntity.LAST_NAME, user.getLastName());
				put(UserEntity.PASSWORD, user.getPassword());
				put(UserEntity.EMAIL_ADDRESS, user.getEmailAddress());
			}
		};

		for (Entry<String, String> entry : userPropertiesMap.entrySet()) {
			if (StringUtils.isNullOrEmpty(entry.getValue())) {
				missingRegistrationFields.add(entry.getKey());
				if (loginFields.contains(entry.getKey())) {
					missingLoginFields.add(entry.getKey());
				}
			}
		}
		return isLogin ? missingLoginFields : missingRegistrationFields;
	}
}
