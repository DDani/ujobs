package org.ubojs.api.resources.representation;



import org.bson.types.ObjectId;
import org.junit.Test;
import org.ujobs.model.User;
// import static org.hamcrest.Matchers.*;

public class UserRepresentationTest {
	
	

	
	@Test
	public void serializesToJSON() throws Exception {

		
	    final User user = new User();
	    user.setId(ObjectId.get());
	    user.setName("Hola");
	    System.err.println(JsonMapperExtension.asJson(user));
	  /*  assertThat("a Person can be serialized to JSON",
	               asJson(person),
	               is(equalTo(jsonFixture("fixtures/person.json"))));*/
	}
}
