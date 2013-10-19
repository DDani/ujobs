package org.ujobs.model;

import java.util.Set;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.ujobs.model.enums.JobOfferStatusType;

@Entity("Job")
public class Job extends Basic {
	
	private String title;
	
	private String description;
	
	private Set<ObjectId> tags;
	
	private Set<ObjectId> requiredAbilities;
	
	private ObjectId creatorId;
	
	private JobOfferStatusType status;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<ObjectId> getTags() {
		return tags;
	}
	public void setTags(Set<ObjectId> tags) {
		this.tags = tags;
	}
	public Set<ObjectId> getRequiredAbilities() {
		return requiredAbilities;
	}
	public void setRequiredAbilities(Set<ObjectId> requiredAbilities) {
		this.requiredAbilities = requiredAbilities;
	}
	public JobOfferStatusType getStatus() {
		return status;
	}
	public void setStatus(JobOfferStatusType status) {
		this.status = status;
	}
	public ObjectId getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(ObjectId creatorId) {
		this.creatorId = creatorId;
	}
	
	
	
}
