package org.ujobs.service.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.model.enums.JobOfferStatusType;
import org.ujobs.service.data.dao.Dao;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.inject.Inject;
import com.mongodb.WriteConcern;
import com.yammer.dropwizard.jersey.params.LongParam;

public class JobServiceImpl implements JobService {
	
	
	@Inject 
	private Dao<Job, ObjectId> jobDAO;
	
	@Inject
	public JobServiceImpl(Dao<Job, ObjectId> jobDAO)
	{
		this.jobDAO = jobDAO;
	}

	@Override
	public Job getJobById(String jobId) {
		return jobDAO.get(ObjectId.massageToObjectId(jobId));
	}

	@Override
	public ObjectId createJob(Job job, User user) {
		
		job.setId(null);
		job.setStatus(JobOfferStatusType.OPEN_FOR_CANDIDATURE);
		job.setCreatorId(ObjectId.massageToObjectId(user.getId()));
		Key<Job> saved = jobDAO.save(job, WriteConcern.SAFE);
		return ObjectId.massageToObjectId(saved.getId());
	}

	@Override
	public ObjectId updateJob(Job job, User user) throws DataValidityException {
		UpdateOperations<Job> updateOperations = jobDAO.createUpdateOperations();
		updateOperations.set("title", job.getTitle());
		updateOperations.set("description", job.getDescription());
		updateOperations.set("requiredAbilities", job.getRequiredAbilities());
		updateOperations.set("tags", job.getTags());
		updateOperations.set("status", job.getStatus());
	
		
		UpdateResults<Job> updated = jobDAO.update(job, updateOperations);
		if (updated.getHadError()) {
			throw new DataValidityException("Unable to save entity");
		} else {
			return ObjectId.massageToObjectId(updated.getNewId());
		}
	}
	
	private void validateJobCreation(Job job) 
	{
		// TODO
		/*
		 * Check that the:
		 * -Job has no id.
		 * -The initial status is set.
		 * -Tags and abilities are set.
		 */
	}
	
	private void validateJobUpdate(Job job) 
	{
		// TODO
		/*
		 * Check that the:
		 * -Job has id and exists?.
		 * -The status is not backwarded, not closed
		 * -Tags and abilities are set and not null.
		 */
	}
	
}
