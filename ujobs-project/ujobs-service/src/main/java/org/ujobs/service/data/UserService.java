package org.ujobs.service.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.model.UserAbility;
import org.ujobs.service.exceptions.DataUpdateException;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.common.base.Optional;

public interface UserService {

	Optional<User> getUserById(ObjectId userId);
	
	User updateUser(User user) throws DataValidityException, DataUpdateException;
	
	User createUser(User user);
	
	UserAbility addUserAbility(User user, UserAbility newUserAbility) throws DataValidityException, DataUpdateException;
	
	UserAbility updateUserAbility(User user, UserAbility userAbility) throws DataValidityException, DataUpdateException;
	
	UserAbility removeUserAbility(User user, ObjectId userAbilityId) throws DataValidityException, DataUpdateException;
	
	List<Job> getPublishedJobsByUserId(ObjectId id);

	List<Candidature> getSponsorshipByUserId(ObjectId id);

	List<Candidature> getCandidaturesByUserId(ObjectId id);

	

	

}
