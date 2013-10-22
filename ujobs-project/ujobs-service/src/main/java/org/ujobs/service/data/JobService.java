package org.ujobs.service.data;


import org.bson.types.ObjectId;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.service.exceptions.DataValidityException;

public interface JobService {

	Job getJobById(ObjectId jobId);

	Job createJob(Job job, User user);

	Job updateJob(Job job, User user) throws DataValidityException;
	
	Candidature addCandidate(Job job, User user);
	
	Candidature removeCandidature(Job job, Candidature candidature);
}
