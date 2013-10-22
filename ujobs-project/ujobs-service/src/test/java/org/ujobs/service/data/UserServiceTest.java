package org.ujobs.service.data;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.mongodb.morphia.Key;
import org.ujobs.model.Candidature;
import org.ujobs.model.Job;
import org.ujobs.model.Tag;
import org.ujobs.model.User;
import org.ujobs.model.UserAbility;
import org.ujobs.service.exceptions.DataUpdateException;
import org.ujobs.service.exceptions.DataValidityException;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class UserServiceTest extends DaoDependentTest {
	
	
	@Test
	public void testBasicSaveUser() {
		UserService userService = getUserService();
		
		User createdUser = userService.createUser(createSimpleUser());
		
		Assert.assertNotNull("Created User is null", createdUser);
		Assert.assertNotNull("Created User does not have an id!", createdUser.getId());
	}
	
	@Test
	public void testFieldResetOnUserSave() {
		UserService userService = getUserService();
		ObjectId userId = ObjectId.get();
		User newUser = createFullUser(userId);
		User createdUser = userService.createUser(newUser);
		
		Assert.assertNotNull("Created User is null", createdUser);
		Assert.assertNotEquals("User id is the same!  it should be automatically set",newUser.getId(), userId);
		Assert.assertNotNull("Created User about has been removed!", createdUser.getAbout());
		Assert.assertNotNull("Created User email has been removed!", createdUser.getEmail());
		Assert.assertNotNull("Created User name has been removed!", createdUser.getName());
		Assert.assertNull("Created User abilities has NOT been removed!", createdUser.getUserAbilities());
		Assert.assertNull("Created User candidatures has NOT been removed!", createdUser.getCandidatures());
		Assert.assertNull("Created User job offers has NOT been removed!", createdUser.getJobOffers());
		Assert.assertNull("Created User sponsored candidatures has NOT been removed!", createdUser.getSponsoredCandidatures());
	}
	
	@Test(expected=DataValidityException.class)
	public void testUpdateUserNotExist() throws DataValidityException, DataUpdateException {
		User user = createSimpleUser();
		UserService userService = getUserService();
		userService.updateUser(user);
	}
	
	@Test
	public void testUpdateUserNonReplazableFields() throws DataValidityException, DataUpdateException {
		// Create a user directly in the DB:
		User user = createFullUser(ObjectId.get());
		
		// Assign the key:
		Key<User> savedUserKey = this.getUserDao().save(user);
		user.setId((ObjectId) savedUserKey.getId());
		user.setName("ReplacedName");
		user.setAbout("ReplacerAbout");
		UserService userService = getUserService();
		User updatedUser = userService.updateUser(user);
		
		Assert.assertEquals("Updated User id is not the same than created one!", updatedUser.getId(), user.getId());
		Assert.assertNotNull("Updated User job offers has been removed!", updatedUser.getJobOffers());
		Assert.assertNotNull("Updated User candidatures has been removed!", updatedUser.getCandidatures());
		Assert.assertNotNull("Updated User sponsored candidatures has been removed!", updatedUser.getSponsoredCandidatures());
	
		
	}
	
	
	private User createSimpleUser() {
		User user = new User();
		user.setName("hola");
		return user;
	}
	
	private User createFullUser(ObjectId userId) {
		User newUser = createSimpleUser();
		newUser.setId(userId);
		newUser.setAbout("About");
		newUser.setEmail("email@email.com");
		newUser.setName("Name");
		
		// Add a dummy job. It should be removed when the service stores the data.
		List<Job> jobOffers = Lists.newArrayList();
		Job job = new Job();
		job.setDescription("dummy");
		jobOffers.add(job);
		newUser.setJobOffers(jobOffers);
		
		
		// Add a dummy candidature. It should be removed when the service stores the data.
		List<Candidature> candidatures = Lists.newArrayList();
		Candidature candidature = new Candidature();
		candidature.setApplicant(newUser);
		candidature.setReason("dummyReason");
		candidatures.add(candidature);
		newUser.setCandidatures(candidatures);
		
		// Add a dummy sponsoredCandidature
		List<Candidature> sponsoredCandidatures = Lists.newArrayList();
		Candidature sponsoredCandidature = new Candidature();
		sponsoredCandidature.setApplicant(newUser);
		sponsoredCandidature.setReason("dummyReason");
		sponsoredCandidatures.add(sponsoredCandidature);
		newUser.setSponsoredCandidatures(sponsoredCandidatures);
		
		// Add abilities. They should be persisted on user creation
		Set<UserAbility> userAbilities = Sets.newHashSet();
		UserAbility userAbility = new UserAbility();
		Tag tag = new Tag();
		tag.setId(ObjectId.get());
		userAbility.getTags().put(tag, 10);
		userAbilities.add(userAbility);
		newUser.setUserAbilities(userAbilities);
		
		return newUser;
	}
}
