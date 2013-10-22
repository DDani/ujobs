package org.ubojs.api.resources;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.types.ObjectId;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.service.data.UserService;
import org.ujobs.service.exceptions.DataUpdateException;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.yammer.metrics.annotation.Timed;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    
	
	private final UserService userService;

	@Inject
	public UserResource(UserService userService)
	{
		this.userService = userService;
	}
	
	 /*
     * Creates a user
     */
    @PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(@Valid User user)
	{
    	User createdUser = userService.createUser(user);
    	   	
		return Response.status(Response.Status.ACCEPTED).entity(createdUser).build();
	}
    
	/*
	 * Get's a user by id:
	 */
    @GET
    @Path("{userId}")
    @Timed
    public Response getUserById(@PathParam("userId") ObjectId userId) 
    {	
    	Optional<User> user = userService.getUserById(userId);
    	if (user.isPresent())
    		return Response.ok(user.get()).build();
    	return Response.status(Status.NOT_FOUND).build();
    }
    
    /*
     * Updates a user
     */
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@Valid User user) throws DataValidityException, DataUpdateException
	{
    	User updatedUser = userService.updateUser(user);
    	   	
		return Response.status(Response.Status.ACCEPTED).entity(updatedUser).build();
	}
    
    @GET
    @Path("/{userId}/jobs")
    public List<Job> getPublishedJobsByUserId(@PathParam("userId") String userId)
    {
    	return userService.getPublishedJobsByUserId(ObjectId.massageToObjectId(userId));
    }
    
    @GET
    @Path("/{userId}/candidatures")
    public List<Candidature> getCandidaturesByUserId(@PathParam("userId") String userId)
    {
    	return userService.getCandidaturesByUserId(ObjectId.massageToObjectId(userId));
    }
    
    @GET
    @Path("/{userId}/sponsorships")
    public List<Candidature> getSponsorshipByUserId(@PathParam("userId") String userId)
    {
    	return userService.getSponsorshipByUserId(ObjectId.massageToObjectId(userId));
    }
    
 
}