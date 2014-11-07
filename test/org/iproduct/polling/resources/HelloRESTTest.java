package org.iproduct.polling.resources;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.iproduct.polling.resources.HelloREST;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Functional tests for {@link org.iproduct.polling.resources.HelloREST HelloREST} resources
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
public class HelloRESTTest {
	public static final String BASE_URI_STRING = "http://localhost:8080/rest-jaxrs/resources";
	private static WebTarget baseTarget;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		URI uri = UriBuilder.fromUri(BASE_URI_STRING).build();
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		baseTarget = client.target(uri);
	}

	@Test
	public void testSayHelloText() {
		String response = baseTarget.path("hello")
				.request(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(response);
		assertEquals(HelloREST.MESSAGE_TEXT, response);
	}

	@Test
	public void testSayHelloXml() {
		String response = baseTarget.path("hello").request(MediaType.TEXT_HTML)
				.get(String.class);
		System.out.println(response);
		assertEquals(HelloREST.MESSAGE_HTML, response);
	}

	@Test
	public void testSayHelloHtml() {
		String response = baseTarget.path("hello").request(MediaType.TEXT_XML)
				.get(String.class);
		System.out.println(response);
		assertEquals(HelloREST.MESSAGE_XML, response);
	}

	@Test
	public void testSayHelloJson() {
		String response = baseTarget.path("hello")
				.request(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println(response);
		assertEquals(HelloREST.MESSAGE_JSON, response);
	}

}
