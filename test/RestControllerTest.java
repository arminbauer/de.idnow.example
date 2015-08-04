import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Ignore;
import org.junit.Test;

import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	JsonNode identifications;

	@Ignore("not needed for completion of the task")
	@Test
	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus(), OK);
			}
		});

	}

	@Ignore("not needed for completion of the task")
	@Test
	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
			}
		});
	}

	@Test
	public void ExampleOne() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode companyOne = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(companyOne).get(10000).getStatus(), OK);

				JsonNode identificationOne = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode identificationTwo = Json.parse("{\"id\": 2, \"name\": \"Klaus Mueller\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationOne).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationTwo).get(10000).getStatus(), OK);

				JsonNode identifcations = Json.parse(WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).getBody());

				/* Following result is expected:
				* 1st identification with id 2
				* 2nd identification with id 1
				*/
				assertEquals(identifcations.get(0).get("id").asInt(), 2);
				assertEquals(identifcations.get(1).get("id").asInt(), 1);
			}
		});
	}

	@Test
	 public void ExampleTwo() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode companyOne = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode companyTwo = Json.parse("{\"id\": 2, \"name\": \"Deutsche Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(companyOne).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(companyTwo).get(10000).getStatus(), OK);

				JsonNode identificationOne = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode identificationTwo = Json.parse("{\"id\": 2, \"name\": \"Klaus Mueller\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationOne).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationTwo).get(10000).getStatus(), OK);

				JsonNode identifcations = Json.parse(WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).getBody());

				/* Following result is expected:
				* 1st identification with id 2
				* 2nd identification with id 1
				*/
				assertEquals(identifcations.get(0).get("id").asInt(), 2);
				assertEquals(identifcations.get(1).get("id").asInt(), 1);
			}
		});
	}

	@Test
	public void ExampleThree() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode companyOne = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode companyTwo = Json.parse("{\"id\": 2, \"name\": \"Deutsche Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(companyOne).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(companyTwo).get(10000).getStatus(), OK);

				JsonNode identificationOne = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode identificationTwo = Json.parse("{\"id\": 2, \"name\": \"Klaus Mueller\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationOne).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationTwo).get(10000).getStatus(), OK);

				JsonNode identifcations = Json.parse(WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).getBody());

				/* Following result is expected:
				* 1st identification with id 1
				* 2nd identification with id 2
				*/
				assertEquals(identifcations.get(0).get("id").asInt(), 1);
				assertEquals(identifcations.get(1).get("id").asInt(), 2);
			}
		});
	}

	@Test
	public void ExampleFour() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode companyOne = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode companyTwo = Json.parse("{\"id\": 2, \"name\": \"Deutsche Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(companyOne).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(companyTwo).get(10000).getStatus(), OK);

				JsonNode identificationOne = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				JsonNode identificationTwo = Json.parse("{\"id\": 2, \"name\": \"Klaus Mueller\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationOne).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationTwo).get(10000).getStatus(), OK);

				JsonNode identifcations = Json.parse(WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).getBody());

				/* Following result is expected:
				* 1st identification with id 1
				* 2nd identification with id 2
				*/
				assertEquals(identifcations.get(0).get("id").asInt(), 1);
				assertEquals(identifcations.get(1).get("id").asInt(), 2);
			}
		});
	}

}
