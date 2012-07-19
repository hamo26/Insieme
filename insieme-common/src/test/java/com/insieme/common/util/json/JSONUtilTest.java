package com.insieme.common.util.json;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.insieme.common.domain.dto.RestEntity;
import com.insieme.common.guice.InsiemeCommonModule;
import com.insieme.common.util.JSONUtil;

public class JSONUtilTest {
	
	private static final String JSON_STRING = "{\"property1\":1,\"property2\":\"2\"}";
	private Injector injector;
	private JSONUtil jsonUtil;

	@Before
	public void setUp() {
		injector = Guice.createInjector(new InsiemeCommonModule());
		jsonUtil = injector.getInstance(TestResource.class).getJsonUtil();
		
	}
	
	@Test
	public void testDeserializeRepresentation() {
		TestPersistentDto result = jsonUtil.deserializeRepresentation(JSON_STRING, TestPersistentDto.class);
		assertEquals(1, result.getTestProperty1());
		assertEquals("2", result.getTestProperty2());
	}

	@Test
	public void testSerializeRepresentation() {
		TestPersistentDto testPersistentDto = new TestPersistentDto(1,"2");
		String result = jsonUtil.serializeRepresentation(testPersistentDto);
		assertEquals(JSON_STRING, result);
	}
	
	private class TestPersistentDto extends RestEntity {
		private int property1;
		private String property2;
		
		public TestPersistentDto(final int property1, final String property2) {
			this.property1 = property1;
			this.property2 = property2;
		}
		
		public int getTestProperty1() {
			return property1;
		}
		
		public String getTestProperty2() {
			return property2;
		}
	}
	
	/**
	 * The Class TestResource.
	 */
	private static class TestResource {

		private final JSONUtil jsonUtil;
	
		@Inject
		private TestResource(@Named("jsonUtil") final JSONUtil jsonUtil) {
			this.jsonUtil = jsonUtil;
			
		}
		public JSONUtil getJsonUtil() {
			return jsonUtil;
		}
	}

}
