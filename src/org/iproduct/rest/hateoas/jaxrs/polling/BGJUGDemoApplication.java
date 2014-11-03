package org.iproduct.rest.hateoas.jaxrs.polling;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.iproduct.rest.hateoas.jaxrs.polling.resources.HelloREST;

@ApplicationPath("/resources")
public class BGJUGDemoApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
	    final Set<Class<?>> classes = new HashSet<>();
	    // register root resource
	    classes.add(HelloREST.class);
	    return classes;
	}
}