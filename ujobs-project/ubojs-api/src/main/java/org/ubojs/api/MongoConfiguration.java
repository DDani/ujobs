package org.ubojs.api;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MongoConfiguration {
	@NotEmpty
	@JsonProperty
	private String uri;

	@NotEmpty
	@JsonProperty
	private String port;

	@NotEmpty
	@JsonProperty
	private String database;

	@NotEmpty
	@JsonProperty
	private String username = "root";

	@NotEmpty
	@JsonProperty
	private String password = "root";


	public String getUri()
	{
		return uri;
	}

	public String getPort()
	{
		return port;
	}

	public String getDatabase()
	{
		return database;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
}
