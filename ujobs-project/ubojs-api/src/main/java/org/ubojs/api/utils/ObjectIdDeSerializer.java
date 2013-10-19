package org.ubojs.api.utils;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ObjectIdDeSerializer extends JsonDeserializer<ObjectId>{

	@Override
	public ObjectId deserialize(JsonParser jp, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		JsonNode oid = ((JsonNode)jp.readValueAsTree()).get("$oid");
		return new ObjectId(oid.asText());
	}

}
