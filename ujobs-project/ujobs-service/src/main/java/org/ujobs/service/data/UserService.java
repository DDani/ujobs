package org.ujobs.service.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.service.exceptions.DataValidityException;

public interface UserService {

	User getUserById(ObjectId userId);
	
	User updateUser(User user) throws DataValidityException;
	
	User createUser(User user);

	List<Job> getPublishedJobsByUserId(ObjectId id);

	List<Candidature> getSponsorshipByUserId(ObjectId id);

	List<Candidature> getCandidaturesByUserId(ObjectId id);

	

}
