package com.insieme.common.guice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.insieme.core.guice.ServiceModule;
import com.insieme.core.user.service.UserService;
import com.insieme.core.user.service.impl.UserServiceImpl;

/**
 * Quick unit test to test guice injections.
 */
public class ServiceModuleTest {

	private Injector injector;

	@Before
	public void createInjector() {
		injector = Guice.createInjector(new ServiceModule());
	}
	
	@Test
	public void tesServerInjectionst() {
		TestServerClass testServerClass = injector.getInstance(TestServerClass.class);
		assertEquals(UserServiceImpl.class,testServerClass.getUserService().getClass());
	}
	
	/**
	 * I am just going to add to this test class.
	 */
	private static class TestServerClass {
		
		@Inject
		@Named("userService")
		private UserService userService;
		
		public UserService getUserService() {
			return this.userService;
		}
	}

}
