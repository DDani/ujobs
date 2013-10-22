package org.ujobs.service.data;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.Tag;
import org.ujobs.model.User;
import org.ujobs.model.UserAbility;
import org.ujobs.service.data.dao.Dao;
import org.ujobs.service.exceptions.DataUpdateException;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

public class UserServiceImpl implements UserService {

	@Inject 
	private Dao<User, ObjectId> userDAO;
	
	@Inject
	private TagService tagService;
	
	@Inject
	public UserServiceImpl(Dao<User, ObjectId> userDAO, TagService tagService)
	{
		this.userDAO = userDAO;
		this.tagService = tagService;
	}

	
	@Override
	public User updateUser(User user) throws DataValidityException, DataUpdateException {
		// Only update user info fields
		Optional<User> userToUpdate = getUserById(user.getId());
		if (userToUpdate.isPresent() == false) {
			throw new DataValidityException("User does not exist");
		}
		UpdateOperations<User> updateOperations = userDAO.createUpdateOperations();
		updateOperations.set("about", user.getAbout());
		updateOperations.set("email", user.getEmail());
		updateOperations.set("name", user.getName());
		
		
		UpdateResults<User> updated = userDAO.update(user, updateOperations);

		if (updated.getHadError()) {
			throw new DataUpdateException("Unable to update entity", updated.getError());
		} else {
			
			return getUserById(user.getId()).get();
		}
	}

	@Override
	public Optional<User> getUserById(ObjectId userId) {
		return Optional.fromNullable(userDAO.get(userId));
	}

	@Override
	public List<Job> getPublishedJobsByUserId(ObjectId userId) {
		Optional<User> user = getUserById(userId);
		if (user.isPresent())
			return user.get().getJobOffers();
		else {
			return null;
		}
	}

	@Override
	public List<Candidature> getSponsorshipByUserId(ObjectId userId) {
		Optional<User> user = getUserById(userId);
		if (user.isPresent())
			return user.get().getSponsoredCandidatures();
		else {
			return null;
		}
	}

	@Override
	public List<Candidature> getCandidaturesByUserId(ObjectId userId) {
		Optional<User> user = getUserById(userId);
		if (user.isPresent())
			return user.get().getCandidatures();
		else {
			return null;
		}
	}

	@Override
	public User createUser(User user) {
		
		// Reset all fields that will be added later or by other calls
		user.setId(null);
		user.setCandidatures(null);
		user.setJobOffers(null);
		user.setSponsoredCandidatures(null);
		user.setUserAbilities(null);
		
		Key<User> savedUserKey = userDAO.save(user);
		user.setId((ObjectId) savedUserKey.getId());
		return user;
	}


	@Override
	public UserAbility addUserAbility(User user, UserAbility newUserAbility) throws DataValidityException, DataUpdateException {
		Optional<User> userToUpdate = getUserById(user.getId());
		
		if (userToUpdate.isPresent() == false) {
			throw new DataValidityException("User does not exist");
		}
		
		/* Loop through the user and compare incomming tags. If the user has another ability with the same tags,
		 * don't allow insert. If the incomming ability has the same id than another one, don't allow it.
		 */
		for (UserAbility userAbility : userToUpdate.get().getUserAbilities()) {
			if (userAbility.getTags().keySet().equals(newUserAbility.getTags().keySet()))
				throw new DataValidityException("The user has already an ability with this tags:" + userAbility.getTags().keySet());
			if (userAbility.getTitle().equals(newUserAbility.getTitle()))
				throw new DataValidityException("The user has already an ability with this title" + userAbility.getTitle());
		}
		
		// Ok, everything is fine. Just add the ability
		newUserAbility.setId(null);
		UpdateOperations<User> updateOperations = userDAO.createUpdateOperations();
		updateOperations.add("userAbilities", newUserAbility);
		UpdateResults<User> updated = userDAO.update(user, updateOperations);
		if (updated.getHadError()) {
			throw new DataUpdateException("Unable to update entity", updated.getError());
		} else {
			
			return newUserAbility;
		}
	}


	@Override
	public UserAbility removeUserAbility(User user, ObjectId userAbilityId) throws DataValidityException, DataUpdateException {
		
		Optional<User> userToUpdate = getUserById(user.getId());
		
		if (userToUpdate.isPresent() == false) {
			throw new DataValidityException("User does not exist");
		}
		
		Optional<UserAbility> userAbility = getUserAbilityById(userToUpdate.get(), userAbilityId);
		
		if (userAbility.isPresent() == false) {
			throw new DataValidityException("User ability does not exist");
		}

		/* Let's remove it, or move to the historic if it has puntuation
		 * TODO: Puntuation is not the only thing matters... later it will be used when the abilities are 
		 * linked to a past job.
		 */
		UpdateOperations<User> updateOperations = userDAO.createUpdateOperations();
		if (userAbility.get().getPuntuation() > 0) {
			updateOperations.add("userHistoricAbilities", userAbility.get());
		}
		updateOperations.removeAll("userAbilities", userAbility.get());
		UpdateResults<User> updated = userDAO.update(user, updateOperations);
		
		if (updated.getHadError()) {
			throw new DataUpdateException("Unable to update entity", updated.getError());
		} else {
			return userAbility.get();
		}
		
	}


	@Override
	public UserAbility updateUserAbility(User user, UserAbility userAbility) throws DataValidityException, DataUpdateException {
		
		Optional<User> userToUpdate = getUserById(user.getId());
		
		if (userToUpdate.isPresent() == false) {
			throw new DataValidityException("User does not exist");
		}
		
		Optional<UserAbility> userAbilityToUpdate = getUserAbilityById(userToUpdate.get(), userAbility.getId());
		
		if (userAbilityToUpdate.isPresent() == false) {
			throw new DataValidityException("User ability does not exist");
		}
		
		/* 
		 * Iterate through tags, and remove the ones that dosn't exist anymore.
		 * TODO: This has sense? The interface should advise the user that it will "kill" her experience
		 */
		Set<Tag> tagsToRemove = Sets.difference( userAbilityToUpdate.get().getTags().keySet(), userAbility.getTags().keySet());
		Set<Tag> tagsToAdd = Sets.difference(userAbility.getTags().keySet(),  userAbilityToUpdate.get().getTags().keySet());
		
		for (Tag tagToRemove : tagsToRemove) {
			userAbilityToUpdate.get().getTags().remove(tagToRemove);
		}
		
		for (Tag tagToAdd : tagsToAdd) {
			userAbilityToUpdate.get().getTags().put(tagToAdd, 0);
		}
		
		UpdateOperations<User> updateOperations = userDAO.createUpdateOperations();
		updateOperations.set("userAbilities", userAbilityToUpdate.get());
		UpdateResults<User> updated = userDAO.update(user, updateOperations);
		
		if (updated.getHadError()) {
			throw new DataUpdateException("Unable to update entity", updated.getError());
		} else {
			return userAbilityToUpdate.get();
		}
	}
	
	private Optional<UserAbility> getUserAbilityById(User user, ObjectId userAbilityId) {
		for (UserAbility userAbility : user.getUserAbilities()) {
			if (userAbility.getId().equals(userAbilityId)) {
				return Optional.fromNullable(userAbility);
			}
		}
		return Optional.absent();
	}

}
