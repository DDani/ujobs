package org.ujobs.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.ujobs.model.enums.CandidatureStatusType;

@Entity("Candidature")
public class Candidature extends Basic  {
	
	private CandidatureStatusType status;
	
	@Reference
	private Job job;
	
	@Reference
	private User applicant;
	
	@Reference
	private User sponsor;
	
	@Reference
	private String reason;
	
	public CandidatureStatusType getStatus() {
		return status;
	}

	public void setStatus(CandidatureStatusType status) {
		this.status = status;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	
	public User getSponsor() {
		return sponsor;
	}

	public void setSponsor(User sponsor) {
		this.sponsor = sponsor;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
}
