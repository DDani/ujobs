package org.ubojs.api.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.mockito.Mockito;
import org.ujobs.model.User;
import org.ujobs.service.data.JobService;
import org.ujobs.service.data.UserService;

import com.yammer.dropwizard.testing.ResourceTest;

public class JobResourceTest  extends ResourceTest {
	
	
	private final JobService jobService = Mockito.mock(JobService.class);
	
	static {
        Logger.getLogger(JobResourceTest.class.toString()).setLevel(Level.OFF);
    }
	
	@Override
	protected void setUpResources() throws Exception {
		
	
		
	}

}
