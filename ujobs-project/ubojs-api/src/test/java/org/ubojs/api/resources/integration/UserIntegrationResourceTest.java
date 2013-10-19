package org.ubojs.api.resources.integration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.ubojs.api.ApplicationConfiguration;
import org.ubojs.api.ApplicationService;
import org.ujobs.model.Job;
import org.ujobs.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.yammer.dropwizard.testing.junit.DropwizardServiceRule;
import com.yammer.dropwizard.testing.JsonHelpers;
public class UserIntegrationResourceTest {
	 
	private int SERVER_PORT = 8080;
	
	protected static Client client;
    protected ObjectMapper mapper = new ObjectMapper();
    protected static WebResource webResource;
    
	@BeforeClass
	public static void RestClient() {
        ClientConfig clientConfig = new DefaultClientConfig();
        client = Client.create(clientConfig);
        webResource = client.resource("http://localhost:8080/service");
    }
	
	@Test
	public void createUser() throws URISyntaxException, UniformInterfaceException, ClientHandlerException, IOException {
	 	
        User user = new User();
        user.setName("Dani");
        User createdUser = webResource.path("user").type(MediaType.APPLICATION_JSON_TYPE).put(User.class, JsonHelpers.asJson(user));
        
        System.err.println(createdUser);
    }
	 
	//@Test
    public void getUser() throws URISyntaxException {
	 	Client client = new Client();
	 	
        String root = String.format("http://localhost:%d/", SERVER_PORT);
        URI uri = UriBuilder.fromUri(root).path("/service/user/1").build();
        User rs = client.resource(uri).get(User.class);
    }
	 
}
