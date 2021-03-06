package org.ubojs.api.utils;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

	@Override
	public void serialize(ObjectId objectId, JsonGenerator generator,
			SerializerProvider serializer) throws IOException,
			JsonProcessingException {
		if(objectId == null) {
			generator.writeNull();
        } else {
        	generator.writeString(objectId.toString());
        }
	}

}
