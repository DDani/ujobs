package org.ubojs.api.resources.representation;

import static com.yammer.dropwizard.testing.FixtureHelpers.fixture;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.ubojs.api.utils.ObjectIdSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yammer.dropwizard.json.ObjectMapperFactory;

public class JsonMapperExtension  {
	
	
	 private static final ObjectMapper MAPPER = new ObjectMapperFactory().build();

	   static { 
	  	  SimpleModule module =
	      	      new SimpleModule("ObjectIdSerializerModule");
	       module.addSerializer(ObjectId.class, new ObjectIdSerializer());

	       MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	       MAPPER.registerModule(module);
	    	
	    }

	    /**
	     * Converts the given object into a canonical JSON string.
	     *
	     * @param object    an object
	     * @return {@code object} as a JSON string
	     * @throws IllegalArgumentException if there is an error encoding {@code object}
	     */
	    public static String asJson(Object object) throws IOException {
	        return MAPPER.writeValueAsString(object);
	    }

	    /**
	     * Converts the given JSON string into an object of the given type.
	     *
	     * @param json     a JSON string
	     * @param klass    the class of the type that {@code json} should be converted to
	     * @param <T>      the type that {@code json} should be converted to
	     * @return {@code json} as an instance of {@code T}
	     * @throws IOException if there is an error reading {@code json} as an instance of {@code T}
	     */
	    public static <T> T fromJson(String json, Class<T> klass) throws IOException {
	        return MAPPER.readValue(json, klass);
	    }

	    /**
	     * Converts the given JSON string into an object of the given type.
	     *
	     * @param json         a JSON string
	     * @param reference    a reference of the type that {@code json} should be converted to
	     * @param <T>          the type that {@code json} should be converted to
	     * @return {@code json} as an instance of {@code T}
	     * @throws IOException if there is an error reading {@code json} as an instance of {@code T}
	     */
	    public static <T> T fromJson(String json, TypeReference<T> reference) throws IOException {
	        return MAPPER.readValue(json, reference);
	    }

	    /**
	     * Loads the given fixture resource as a normalized JSON string.
	     *
	     * @param filename    the filename of the fixture
	     * @return the contents of {@code filename} as a normalized JSON string
	     * @throws IOException if there is an error parsing {@code filename}
	     */
	    public static String jsonFixture(String filename) throws IOException {
	        return MAPPER.writeValueAsString(MAPPER.readValue(fixture(filename), JsonNode.class));
	    }
	
}
