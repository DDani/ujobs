package org.ujobs.service.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.service.data.dao.Dao;
import org.ujobs.service.exceptions.DataValidityException;


import com.google.inject.Inject;

public class UserServiceImpl implements UserService {

	@Inject 
	private Dao<User, ObjectId> userDAO;
	
	@Inject
	public UserServiceImpl(Dao<User, ObjectId> userDAO)
	{
		this.userDAO = userDAO;
	}

	
	@Override
	public User updateUser(User user) throws DataValidityException {
		// Only update user info fields
		User userToUpdate = userDAO.get(user.getId());
		if (userToUpdate == null) {
			throw new DataValidityException("User does not exist");
		}
		userToUpdate.setAbout(user.getAbout());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setName(user.getName());
		// TODO: manage abilities update
		return user;
	}

	@Override
	public User getUserById(ObjectId userId) {
		return userDAO.get(userId);
	}

	@Override
	public List<Job> getPublishedJobsByUserId(ObjectId userId) {
		User user = getUserById(userId);
		if (userId != null)
			return user.getJobOffers();
		else {
			return null;
		}
	}

	@Override
	public List<Candidature> getSponsorshipByUserId(ObjectId userId) {
		User user = getUserById(userId);
		if (userId != null)
			return user.getSponsoredCandidatures();
		else {
			return null;
		}
	}

	@Override
	public List<Candidature> getCandidaturesByUserId(ObjectId userId) {
		User user = getUserById(userId);
		if (userId != null)
			return user.getCandidatures();
		else {
			return null;
		}
	}

	@Override
	public User createUser(User user) {
		
		// Reset all fields that will be added later.
		user.setId(null);
		user.setCandidatures(null);
		user.setJobOffers(null);
		user.setSponsoredCandidatures(null);
		
		Key<User> savedUserKey = userDAO.save(user);
		user.setId((ObjectId) savedUserKey.getId());
		return user;
	}

}
