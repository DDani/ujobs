package org.ujobs.model;

import java.util.List;
import java.util.Set;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Entity("User")
public class User extends Basic   {
	
	private String name;
	
	private String email;
	
	private String about;
	
	@Embedded
	private Set<UserAbility> userAbilities = Sets.newHashSet();
	
	@Embedded
	private Set<UserAbility> userHistoricAbilities  = Sets.newHashSet();
	
	@Reference
	private List<Job> jobOffers = Lists.newArrayList();
	
	@Reference
	private List<Candidature> candidatures  = Lists.newArrayList();
	
	@Reference
	private List<Candidature> sponsoredCandidatures  = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserAbility> getUserAbilities() {
		return userAbilities;
	}

	public void setUserAbilities(Set<UserAbility> userAbilities) {
		this.userAbilities = userAbilities;
	}
	
	public Set<UserAbility> getUserHistoricAbilities() {
		return userHistoricAbilities;
	}

	public void setUserHistoricAbilities(Set<UserAbility> userHistoricAbilities) {
		this.userHistoricAbilities = userHistoricAbilities;
	}

	public List<Job> getJobOffers() {
		return jobOffers;
	}

	public void setJobOffers(List<Job> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public List<Candidature> getCandidatures() {
		return candidatures;
	}

	public void setCandidatures(List<Candidature> candidatures) {
		this.candidatures = candidatures;
	}

	public List<Candidature> getSponsoredCandidatures() {
		return sponsoredCandidatures;
	}

	public void setSponsoredCandidatures(List<Candidature> sponsoredCandidatures) {
		this.sponsoredCandidatures = sponsoredCandidatures;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((userAbilities == null) ? 0 : userAbilities.hashCode());
		result = prime * result + ((about == null) ? 0 : about.hashCode());
		result = prime * result
				+ ((candidatures == null) ? 0 : candidatures.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((jobOffers == null) ? 0 : jobOffers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((sponsoredCandidatures == null) ? 0 : sponsoredCandidatures
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userAbilities == null) {
			if (other.userAbilities != null)
				return false;
		} else if (!userAbilities.equals(other.userAbilities))
			return false;
		if (about == null) {
			if (other.about != null)
				return false;
		} else if (!about.equals(other.about))
			return false;
		if (candidatures == null) {
			if (other.candidatures != null)
				return false;
		} else if (!candidatures.equals(other.candidatures))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (jobOffers == null) {
			if (other.jobOffers != null)
				return false;
		} else if (!jobOffers.equals(other.jobOffers))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sponsoredCandidatures == null) {
			if (other.sponsoredCandidatures != null)
				return false;
		} else if (!sponsoredCandidatures.equals(other.sponsoredCandidatures))
			return false;
		return true;
	}

	
	
}
