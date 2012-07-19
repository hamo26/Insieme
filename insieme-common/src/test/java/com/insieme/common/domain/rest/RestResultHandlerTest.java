package com.insieme.common.domain.rest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.domain.dto.InsiemeExceptionEntity;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.guice.InsiemeCommonModule;

public class RestResultHandlerTest {
	private static final String VALID_USER = "{\"user-id\":1,\"password\":\"2\"}";
	private static final String INSIEME_EXCEPTION = "{\"insieme-error\":\"1\"}";
	
	@Test
	public void testResultHandlerSuccess() {
		TestClass testClass = Guice.createInjector(new InsiemeCommonModule()).getInstance(TestClass.class);
		RestResult<UserEntity> restResult = testClass.getRestResultHandler().createRestResult(VALID_USER, UserEntity.class);
		assertTrue(restResult.isSuccessfull());
		assertNull(restResult.getError());
		UserEntity userResult = restResult.getRestResult();
		assertTrue(userResult.getUserId().equals("1"));
		assertTrue(userResult.getPassword().equals("2"));
	}
	
	@Test
	public void testResultHandlerFailure() {
		TestClass testClass = Guice.createInjector(new InsiemeCommonModule()).getInstance(TestClass.class);
		RestResult<UserEntity> restResult = testClass.getRestResultHandler().createRestResult(INSIEME_EXCEPTION, UserEntity.class);
		assertTrue(restResult.isFailure());
		assertNull(restResult.getRestResult());
		InsiemeExceptionEntity errorResult = restResult.getError();
		assertTrue(errorResult.getException().equals("1"));
	}

	private static class TestClass {
		private final RestResultHandler restResultHandler;
		
		@Inject
		private TestClass(@Named("restResultHandler") final RestResultHandler restResultHandler) {
			this.restResultHandler = restResultHandler;
			
		}
		
		public RestResultHandler getRestResultHandler() {
			return this.restResultHandler;
		}
	}
}
