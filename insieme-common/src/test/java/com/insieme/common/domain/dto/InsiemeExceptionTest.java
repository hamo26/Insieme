package com.insieme.common.domain.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.insieme.common.guice.InsiemeCommonModule;

public class InsiemeExceptionTest {

	@Test
	public void test() {
		TestClass testClass = Guice.createInjector(new InsiemeCommonModule()).getInstance(TestClass.class);
		String serializedJsonException = testClass.get().serializeJsonException();
		assertEquals("{\"insieme-error\":\"test\"}", serializedJsonException);
	}
	
	private static class TestClass {

		@Inject
		private InsiemeExceptionFactory insiemeExceptionFactory;
		
		private TestClass() {
		}
		
		public InsiemeException get() {
			return this.insiemeExceptionFactory.createInsiemeException("test");
		}
	}

}
