import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.util.Iterator;

import org.junit.Test;

import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * Simple (JUnit) tests that can call all parts of a play app. If you are
 * interested in mocking a whole application, see the wiki for more details.
 *
 */
public class RestControllerTest {

	JsonNode identifications;

	@Test
	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).getStatus(),
						OK);
			}
		});

	}

	@Test
	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(
						"{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);
			}
		});

	}

	@Test
	public void test_pendingIdentification1() throws Exception {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode company = Json.parse(
					"{\"name\": \"Company\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

			JsonNode identification1 = Json
					.parse("{\"name\": \"User1\", \"time\": 1501494913, \"waiting_time\": 30, \"companyid\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000)
					.getStatus(), OK);

			JsonNode identification2 = Json
					.parse("{\"name\": \"User2\", \"time\": 1501494913, \"waiting_time\": 45, \"companyid\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000)
					.getStatus(), OK);

			JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();

			Iterator<JsonNode> elements = jsonNode.elements();
			JsonNode jsonNode1 = elements.next();
			assertEquals(jsonNode1.get("name").asText(), "User2");

			JsonNode jsonNode2 = elements.next();
			assertEquals(jsonNode2.get("name").asText(), "User1");
		});
	}

	@Test
	public void test_pendingIdentification2() throws Exception {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {

			JsonNode company1 = Json.parse(
					"{\"name\": \"Company1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

			JsonNode company2 = Json.parse(
					"{\"name\": \"Company2\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

			JsonNode identification1 = Json
					.parse("{\"name\": \"User1\", \"time\": 1501494913, \"waiting_time\": 30, \"companyid\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000)
					.getStatus(), OK);

			JsonNode identification2 = Json
					.parse("{\"name\": \"User2\", \"time\": 1501494913, \"waiting_time\": 30, \"companyid\": 2}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000)
					.getStatus(), OK);

			JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();

			Iterator<JsonNode> elements = jsonNode.elements();
			JsonNode jsonNode1 = elements.next();
			assertEquals(jsonNode1.get("name").asText(), "User2");

			JsonNode jsonNode2 = elements.next();
			assertEquals(jsonNode2.get("name").asText(), "User1");
		});
	}

	@Test
	public void test_pendingIdentification3() throws Exception {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode company1 = Json.parse(
					"{\"name\": \"Company1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

			JsonNode company2 = Json.parse(
					"{\"name\": \"Company2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

			JsonNode identification1 = Json
					.parse("{\"name\": \"User1\", \"time\": 1501494913, \"waiting_time\": 45, \"companyid\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000)
					.getStatus(), OK);

			JsonNode identification2 = Json
					.parse("{\"name\": \"User2\", \"time\": 1501494913, \"waiting_time\": 30, \"companyid\": 2}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000)
					.getStatus(), OK);

			JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();

			Iterator<JsonNode> elements = jsonNode.elements();
			JsonNode jsonNode1 = elements.next();
			assertEquals(jsonNode1.get("name").asText(), "User1");

			JsonNode jsonNode2 = elements.next();
			assertEquals(jsonNode2.get("name").asText(), "User2");
		});
	}

	@Test
	public void test_pendingIdentification4() throws Exception {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode company1 = Json.parse(
					"{\"name\": \"Comany1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

			JsonNode company2 = Json.parse(
					"{\"name\": \"Company2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.8}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

			JsonNode identification1 = Json
					.parse("{\"name\": \"User1\", \"time\": 1501494913, \"waiting_time\": 45, \"companyid\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000)
					.getStatus(), OK);

			JsonNode identification2 = Json
					.parse("{\"name\": \"User2\", \"time\": 1501494913, \"waiting_time\": 30, \"companyid\": 2}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000)
					.getStatus(), OK);

			JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();

			Iterator<JsonNode> elements = jsonNode.elements();
			JsonNode jsonNode1 = elements.next();
			assertEquals(jsonNode1.get("name").asText(), "User1");

			JsonNode jsonNode2 = elements.next();
			assertEquals(jsonNode2.get("name").asText(), "User2");
		});
	}

}
