package com.insieme.common.guice;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.routing.Router;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.insieme.core.guice.SelfInjectingServerResource;
import com.insieme.core.guice.SelfInjectingServerResourceModule;
import com.insieme.core.guice.ServiceModule;
import com.insieme.core.user.service.UserService;
import com.insieme.core.user.service.impl.UserServiceImpl;

public class SelfInjectingServerResourceModuleTest extends Application {
	 @Test public void testReturnsMessage() {
	        ClientResource client = new ClientResource("http://localhost:8118");
	        String msg = client.getChild("/hello", HelloResource.class).getMessage();
	        assertEquals(UserServiceImpl.class.getName(), msg);
	    }
	 
	    @Before public void createInjector() {
	        Guice.createInjector(
	            new ServiceModule(),
	            new SelfInjectingServerResourceModule()
	        );
	    }
	 
	    @Before public void startComponent() throws Exception {
	        component = new Component();
	        component.getServers().add(Protocol.HTTP, 8118);
	        component.getDefaultHost().attachDefault(this);
	        component.start();
	    }
	 
	    @After public void stopComponent() throws Exception {
	        component.stop();
	    }
	 
	    private volatile Component component;
	 
	 
	    @Override public Restlet createInboundRoot() {
	        Router router = new Router(getContext());
	        router.attach("/hello", HelloServerResource.class);
	        return router;
	    }
	 
	    public interface HelloResource {
	        @Get String getMessage();
	    }
	 
	    public static class HelloServerResource
	            extends SelfInjectingServerResource implements HelloResource {
	 
	        @Override public String getMessage() {
	            return userService.getClass().getName();
	        }
	 
	        @Inject @Named("userService") private UserService userService;
	    }
	 
	    static class TestModule extends AbstractModule {
	        @Provides @Named(HELLO_KEY) String helloMessage() {
	            return HELLO_MSG;
	        }
	        protected void configure() {}
	    }
	 
	    static final String HELLO_KEY = "hello.message";
	    static final String HELLO_MSG = "This resource was injected by Guice!";
}
