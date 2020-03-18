import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

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
				assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus(), OK);
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
	public void testExample1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(
						"{\"id\": 11, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 11, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 11}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);
				identification = Json.parse(
						"{\"id\": 12, \"name\": \"Hans Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 11}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);

				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(OK, response.getStatus());
				assertEquals(
						"[{\"id\":12,\"name\":\"Hans Huber\",\"time\":1435667215,\"waiting_time\":45,\"companyid\":11},{\"id\":11,\"name\":\"Peter Huber\",\"time\":1435667215,\"waiting_time\":30,\"companyid\":11}]",
						response.getBody());
			}
		});

	}

	@Test
	public void testExample2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(
						"{\"id\": 21, \"name\": \"Company1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);
				company = Json.parse(
						"{\"id\": 22, \"name\": \"Company2\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.9}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 21, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 21}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);
				identification = Json.parse(
						"{\"id\": 22, \"name\": \"Hans Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 22}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);

				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(OK, response.getStatus());
				assertEquals(
						"[{\"id\":22,\"name\":\"Hans Huber\",\"time\":1435667215,\"waiting_time\":30,\"companyid\":22},{\"id\":21,\"name\":\"Peter Huber\",\"time\":1435667215,\"waiting_time\":30,\"companyid\":21}]",
						response.getBody());
			}
		});

	}

	@Test
	public void testExample3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(
						"{\"id\": 31, \"name\": \"Company1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);
				company = Json.parse(
						"{\"id\": 32, \"name\": \"Company2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 31, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 31}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);
				identification = Json.parse(
						"{\"id\": 32, \"name\": \"Hans Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 32}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);

				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(OK, response.getStatus());
				assertEquals(
						"[{\"id\":31,\"name\":\"Peter Huber\",\"time\":1435667215,\"waiting_time\":30,\"companyid\":31},{\"id\":32,\"name\":\"Hans Huber\",\"time\":1435667215,\"waiting_time\":30,\"companyid\":32}]",
						response.getBody());
			}
		});

	}

	@Test
	public void testExample4() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(
						"{\"id\": 41, \"name\": \"Company1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);
				company = Json.parse(
						"{\"id\": 42, \"name\": \"Company2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.8}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 41, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 41}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);
				identification = Json.parse(
						"{\"id\": 42, \"name\": \"Hans Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 42}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);

				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(OK, response.getStatus());
				assertEquals(
						"[{\"id\":42,\"name\":\"Hans Huber\",\"time\":1435667215,\"waiting_time\":30,\"companyid\":42},{\"id\":41,\"name\":\"Peter Huber\",\"time\":1435667215,\"waiting_time\":45,\"companyid\":41}]",
						response.getBody());
			}
		});

	}

	@Test
	public void testUnknownCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode identification = Json.parse(
						"{\"id\": 51, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 12356}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), BAD_REQUEST);
			}
		});

	}

}
