package org.ubojs.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ujobs.model.Job;
import org.ujobs.service.data.JobService;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.inject.Inject;
import com.yammer.metrics.annotation.Timed;

@Path("/job")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {

	private JobService jobService;

	@Inject
	public JobResource(JobService jobService) {
		this.jobService = jobService;
	}

	@GET
	@Path("/{jobId}")
	@Timed
	public Job getJobByID(@PathParam("jobId") String jobId)
	{
		return jobService.getJobById(jobId);
	}
	
	@PUT
	@Timed
	public Response createJob(Job job)
	{
		jobService.createJob(job, null);
		return  Response.status(Response.Status.ACCEPTED).entity(job).build();
	}
	
	@POST
	@Timed
	public Response updateJob(Job job)
	{
		try {
			jobService.updateJob(job, null);
			return  Response.status(Response.Status.ACCEPTED).entity(job).build();
		} catch (DataValidityException e) {
			e.printStackTrace();
			return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(job).build();
		}
		
	}
	
	
	
	
}
