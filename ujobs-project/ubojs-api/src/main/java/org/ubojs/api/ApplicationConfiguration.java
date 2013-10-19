package org.ubojs.api;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

/**
 * Default configuration for uJobs
 *
 */
public class ApplicationConfiguration extends Configuration {
	
	@Valid
	@NotNull
	@JsonProperty
	private MongoConfiguration mongo = new MongoConfiguration();

	public MongoConfiguration getMongo()
	{
		return mongo;
	}
}