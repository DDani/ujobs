package org.ujobs.service.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.service.exceptions.DataValidityException;

import com.yammer.dropwizard.jersey.params.LongParam;

public interface JobService {

	Job getJobById(String jobId);

	ObjectId createJob(Job job, User user);

	ObjectId updateJob(Job job, User user) throws DataValidityException;

	
}
