package org.ujobs.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import com.google.common.collect.Maps;

@Entity("UserAbility")
public class UserAbility extends Basic {
	
	private String title;
	
	private String description;
	
	@Transient
	private Integer puntuation;
	
	private Map<Tag,Integer> tags = Maps.newConcurrentMap(); 
	
	public Map<Tag,Integer> getTags() {
		return tags;
	}
	
	public void setTags(Map<Tag,Integer> tags) {
		this.tags = tags;
	}
	
	public Integer getPuntuation() {
		if (puntuation == null) {
			for (Integer tagPuntuation : tags.values())
				puntuation = tagPuntuation + puntuation;
		}
		return puntuation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
