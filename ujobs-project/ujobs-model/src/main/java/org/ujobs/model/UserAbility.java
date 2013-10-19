package org.ujobs.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity("UserAbility")
public class UserAbility {
	
	@Reference
	public Ability ability;
	
	public Long puntuation;
	
	
	public Ability getAbility() {
		return ability;
	}
	public void setAbility(Ability ability) {
		this.ability = ability;
	}
	public Long getPuntuation() {
		return puntuation;
	}
	public void setPuntuation(Long puntuation) {
		this.puntuation = puntuation;
	}
	
}
