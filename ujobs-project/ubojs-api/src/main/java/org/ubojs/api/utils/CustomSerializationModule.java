package org.ubojs.api.utils;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomSerializationModule extends SimpleModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6709512416674780733L;
	
	public CustomSerializationModule() {
		 addDeserializer(ObjectId.class, new ObjectIdDeSerializer());
	       addSerializer(ObjectId.class, new ObjectIdSerializer());
	}
}
