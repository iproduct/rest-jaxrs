package org.iproduct.rest.hateoas.jaxrs.polling.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.*;

@Path("/hello")
public class HelloREST {
	static final String MESSAGE_TEXT = 
			"Hello BGJUG, from JAX-RS!";
	static final String MESSAGE_XML = 
			"<?xml version=\"1.0\"?>"
			+ "<hello>Hello BGJUG, from JAX-RS!</hello>";
	static final String MESSAGE_HTML = 
			"<html><title>Hello BGJUG, from JAX-RS!</title>"
			+ "<body><h1>Hello BGJUG, from JAX-RS!</body></h1></html> ";
	static final String MESSAGE_JSON = 
			"{\"message\" : \"" + MESSAGE_TEXT + "\"}";


	@GET
	@Produces(TEXT_PLAIN)
	public String sayHelloText() {
		return MESSAGE_TEXT;
	}
	@GET
	@Produces(TEXT_HTML)
	public String sayHelloHtml() {
		return MESSAGE_HTML;
	}
	
	@GET
	@Produces(TEXT_XML)
	public String sayHelloXml() {
		return MESSAGE_XML;
	}

	@GET
	@Produces(APPLICATION_JSON)
	public String sayHelloJson() {
		return MESSAGE_JSON;
	}


}
