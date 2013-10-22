package org.ubojs.api.resources;

import java.util.logging.Level;
import java.util.logging.Logger;


import org.ubojs.api.resources.UserResource;
import org.ujobs.model.User;
import org.ujobs.service.data.UserService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Optional;
import com.yammer.dropwizard.testing.ResourceTest;
import static org.fest.assertions.api.Assertions.assertThat;

public class UserResourceTest extends ResourceTest {
	
	private final UserService userService = Mockito.mock(UserService.class);
	private User user = new User();
	private ObjectId userId = ObjectId.get();
	
	static {
        Logger.getLogger(UserResourceTest.class.toString()).setLevel(Level.OFF);
    }
	
	
	@Override
	protected void setUpResources() throws Exception {
		
		user = new User();
		user.setId(userId);
		user.setName("Dani");
		
		Mockito.when(userService.getUserById(userId)).thenReturn(Optional.fromNullable(user));
		
        addResource(new UserResource(userService));
		
	}
	
	/* Simply test that the user exists */
	@Test
    public void userGet() throws Exception {
        assertThat(client().resource("/user/" + userId).get(User.class))
                   .isEqualTo(user);
        
        Mockito.verify(userService).getUserById(userId);
    }
	
	
	
	

	
	
}
