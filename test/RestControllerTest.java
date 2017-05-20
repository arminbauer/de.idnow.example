import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.CREATED;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.Company;
import model.Identification;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	private WSResponse get(String urlEndpoint) {
		return WS.url(String.format("http://localhost:3333/api/v1/%s", urlEndpoint)).get().get(10000);
	}
	
	private WSResponse post(String urlEndpoint, JsonNode jsonNode) {
		return WS.url(String.format("http://localhost:3333/api/v1/%s", urlEndpoint)).post(jsonNode).get(10000);
	}
	
	@Before
	public void init() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
				WSResponse wsResponse = get("cleardata");
				assertThat(wsResponse.getStatus(), is(OK));
		});
	}

	@Test
	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
				WSResponse wsResponse = get("identifications");
				assertThat(wsResponse.getStatus(), is(OK));
		});

	}

	/**
	 * Fixed unit test
	 */
	@Test
	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				WSResponse addCompanyResponse = post("addCompany",company);
				assertThat(addCompanyResponse.getStatus(), is(CREATED));
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				WSResponse postIdentResponse = post("startIdentification",identification);
				assertThat(postIdentResponse.getStatus(), is(CREATED));
		});

	}
	
	/**
	 * 
	 */
	@Test
	public void exampleOne() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			Logger.info("exampleOne");
			Company c = new Company(1, "AmericanExpress", 60, 0.9f, 0.95f);
			Identification i1 = new Identification(1, "id1", 10, 30, 1);
			Identification i2 = new Identification(2, "id2", 10, 45, 1);
			
			assertThat(post("addCompany",Json.toJson(c)).getStatus(), is(CREATED));
			assertThat(post("startIdentification",Json.toJson(i1)).getStatus(), is(CREATED));
			assertThat(post("startIdentification",Json.toJson(i2)).getStatus(), is(CREATED));
			
			WSResponse idents = get("identifications");
			assertThat(idents.getStatus(), is(OK));
			JsonNode json = idents.asJson();
			assertThat(json.isArray(), is(true));
			
			assertThat(json.get(0), is(notNullValue()));
			ObjectNode objectOne = (ObjectNode) json.get(0);
			assertThat(objectOne.get("id").asLong(), is(2l));
			
			assertThat(json.get(1), is(notNullValue()));
			ObjectNode objectTwo = (ObjectNode) json.get(1);
			assertThat(objectTwo.get("id").asLong(), is(1l));
			
		});
	}
	
	/**
	 * 
	 */
	@Test
	public void exampleTwo() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			Logger.info("exampleTwo");
			Company c1 = new Company(1, "AmericanExpress", 60, 0.9f, 0.95f);
			Company c2 = new Company(2, "Citibank", 60, 0.9f, 0.90f);
			Identification i1 = new Identification(1, "id1", 10, 30, 1);
			Identification i2 = new Identification(2, "id2", 10, 30, 2);
			
			assertThat(post("addCompany",Json.toJson(c1)).getStatus(), is(CREATED));
			assertThat(post("addCompany",Json.toJson(c2)).getStatus(), is(CREATED));
			assertThat(post("startIdentification",Json.toJson(i1)).getStatus(), is(CREATED));
			assertThat(post("startIdentification",Json.toJson(i2)).getStatus(), is(CREATED));
			
			WSResponse idents = get("identifications");
			assertThat(idents.getStatus(), is(OK));
			JsonNode json = idents.asJson();
			assertThat(json.isArray(), is(true));
			
			assertThat(json.get(0), is(notNullValue()));
			ObjectNode objectOne = (ObjectNode) json.get(0);
			assertThat(objectOne.get("id").asLong(), is(2l));
			
			assertThat(json.get(1), is(notNullValue()));
			ObjectNode objectTwo = (ObjectNode) json.get(1);
			assertThat(objectTwo.get("id").asLong(), is(1l));
		});
	}
	
	/**
	 * 
	 */
	@Test
	public void exampleThree() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			Logger.info("exampleThree");
			Company c1 = new Company(1, "AmericanExpress", 60, 0.9f, 0.95f);
			Company c2 = new Company(2, "Citibank", 120, 0.8f, 0.95f);
			Identification i1 = new Identification(1, "id1", 10, 30, 1);
			Identification i2 = new Identification(2, "id2", 10, 30, 2);
			
			assertThat(post("addCompany",Json.toJson(c1)).getStatus(), is(CREATED));
			assertThat(post("addCompany",Json.toJson(c2)).getStatus(), is(CREATED));
			assertThat(post("startIdentification",Json.toJson(i1)).getStatus(), is(CREATED));
			assertThat(post("startIdentification",Json.toJson(i2)).getStatus(), is(CREATED));
			
			WSResponse idents = get("identifications");
			assertThat(idents.getStatus(), is(OK));
			JsonNode json = idents.asJson();
			assertThat(json.isArray(), is(true));
			
			assertThat(json.get(0), is(notNullValue()));
			ObjectNode objectOne = (ObjectNode) json.get(0);
			assertThat(objectOne.get("id").asLong(), is(1l));
			
			assertThat(json.get(1), is(notNullValue()));
			ObjectNode objectTwo = (ObjectNode) json.get(1);
			assertThat(objectTwo.get("id").asLong(), is(2l));
		});
	}
	
	/**
	 * 
	 */
	@Test
	public void exampleFour() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			Logger.info("exampleFour");
			Company c1 = new Company(1, "AmericanExpress", 60, 0.9f, 0.95f);
			Company c2 = new Company(2, "Citibank", 120, 0.8f, 0.80f);
			Identification i1 = new Identification(1, "id1", 10, 45, 1);
			Identification i2 = new Identification(2, "id2", 10, 30, 2);
			
			assertThat(post("addCompany",Json.toJson(c1)).getStatus(), is(CREATED));
			assertThat(post("addCompany",Json.toJson(c2)).getStatus(), is(CREATED));
			assertThat(post("startIdentification",Json.toJson(i1)).getStatus(), is(CREATED));
			assertThat(post("startIdentification",Json.toJson(i2)).getStatus(), is(CREATED));
			
			WSResponse idents = get("identifications");
			assertThat(idents.getStatus(), is(OK));
			JsonNode json = idents.asJson();
			assertThat(json.isArray(), is(true));
			
			assertThat(json.get(0), is(notNullValue()));
			ObjectNode objectOne = (ObjectNode) json.get(0);
			assertThat(objectOne.get("id").asLong(), is(1l));
			
			assertThat(json.get(1), is(notNullValue()));
			ObjectNode objectTwo = (ObjectNode) json.get(1);
			assertThat(objectTwo.get("id").asLong(), is(2l));
			
		});
	}
	
	
	/**
	 * 
	 */
	@Test
	public void createDuplicateCompany() {
		Logger.info("createDuplicateCompany");
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			Company c1 = new Company(1, "AmericanExpress", 60, 0.9f, 0.95f);
			assertThat(post("addCompany",Json.toJson(c1)).getStatus(), is(CREATED));
			WSResponse res = post("addCompany",Json.toJson(c1));
			assertThat(res.getStatus(), is(BAD_REQUEST));
			JsonNode json = res.asJson();
			assertThat(json, is(instanceOf(ObjectNode.class)));
			String errorValue = ((ObjectNode) json).get("error").asText();
			assertThat(errorValue, containsString(String.valueOf(c1.getId())));
			Logger.info("response is {}", errorValue);
		});
	}
	
	/**
	 * 
	 */
	@Test
	public void createDuplicateIdentification() {
		Logger.info("createDuplicateIdentification");
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			Company c1 = new Company(1, "AmericanExpress", 60, 0.9f, 0.95f);
			assertThat(post("addCompany",Json.toJson(c1)).getStatus(), is(CREATED));
			
			Identification i1 = new Identification(1, "id1", System.currentTimeMillis(), 10, 1);
			assertThat(post("startIdentification",Json.toJson(i1)).getStatus(), is(CREATED));
			
			WSResponse res = post("startIdentification",Json.toJson(i1));
			assertThat(res.getStatus(), is(BAD_REQUEST));
			JsonNode json = res.asJson();
			assertThat(json, is(instanceOf(ObjectNode.class)));
			String errorValue = ((ObjectNode) json).get("error").asText();
			assertThat(errorValue, containsString(String.valueOf(i1.getId())));
			Logger.info("response is {}", errorValue);
		});
	}
	
	
	/**
	 * 
	 */
	@Test
	public void createIdentificationWhenNoCompanyIsAvailable() {
		Logger.info("createIdentificationWhenNoCompanyIsAvailable");
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			Identification i1 = new Identification(1, "id1", System.currentTimeMillis(), 10, 1);
			assertThat(post("startIdentification",Json.toJson(i1)).getStatus(), is(BAD_REQUEST));
		});
	}
	
}
