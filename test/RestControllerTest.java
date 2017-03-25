import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import models.Company;
import models.Identification;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;

/**
 *
 * Simple (JUnit) tests that can call all parts of a play app. If you are
 * interested in mocking a whole application, see the wiki for more details.
 *
 */
public class RestControllerTest {

	JsonNode identifications;

	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus(), OK);
			}
		});

	}

	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/resetRepository").get().get(10000).getStatus(), OK);

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
	public void pendingIdentificationsEmpty() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/v1/resetRepository").get().get(10000).getStatus(), OK);
				JsonNode pendingIdentifications = WS.url("http://localhost:3333/api/v1/identifications").get()
						.get(10000).asJson();				
				if (pendingIdentifications.isArray()) {				
					assertEquals(pendingIdentifications.size(), 0);
				} else
					fail();

			}
		});
	}

	@Test
	public void pendingIdentificationsExampleOne() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/resetRepository").get().get(10000).getStatus(), OK);

				JsonNode company = Json.parse(
						"{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 1, \"name\": \"Peter Huber-11\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);

				JsonNode identification2 = Json.parse(
						"{\"id\": 2, \"name\": \"Ralf Huber-21\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000)
						.getStatus(), OK);

				JsonNode pendingIdentifications = WS.url("http://localhost:3333/api/v1/identifications").get()
						.get(10000).asJson();

				JsonNode x1 = pendingIdentifications.get(0);
				JsonNode x2 = pendingIdentifications.get(1);

				assertEquals(x1.get("id").asInt(), 2);
				assertEquals(x2.get("id").asInt(), 1);

			}
		});

	}

	@Test
	public void pendingIdentificationsExampleTwo() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/v1/resetRepository").get().get(10000).getStatus(), OK);

				JsonNode company1 = Json.parse(
						"{\"id\": 10, \"name\": \"Test Bank-1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(),
						OK);

				JsonNode company2 = Json.parse(
						"{\"id\": 20, \"name\": \"Test Bank-2\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 11, \"name\": \"Peter Huber-111\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 10}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);

				JsonNode identification2 = Json.parse(
						"{\"id\": 21, \"name\": \"Ralf Huber-211\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 20}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000)
						.getStatus(), OK);

				JsonNode pendingIdentifications = WS.url("http://localhost:3333/api/v1/identifications").get()
						.get(10000).asJson();

				assertEquals(pendingIdentifications.get(0).get("id").asInt(), 21);
				assertEquals(pendingIdentifications.get(1).get("id").asInt(), 11);

			}
		});
	}

	@Test
	public void pendingIdentificationsExampleThree() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/v1/resetRepository").get().get(10000).getStatus(), OK);

				JsonNode company1 = Json.parse(
						"{\"id\": 1, \"name\": \"Test Bank-1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(),
						OK);

				JsonNode company2 = Json.parse(
						"{\"id\": 2, \"name\": \"Test Bank-2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);

				JsonNode identification2 = Json.parse(
						"{\"id\": 2, \"name\": \"Ralf Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000)
						.getStatus(), OK);

				JsonNode pendingIdentifications = WS.url("http://localhost:3333/api/v1/identifications").get()
						.get(10000).asJson();

				assertEquals(pendingIdentifications.get(0).get("id").asInt(), 1);
				assertEquals(pendingIdentifications.get(1).get("id").asInt(), 2);

			}
		});

	}

	@Test
	public void pendingIdentificationsExampleFour() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/v1/resetRepository").get().get(10000).getStatus(), OK);

				JsonNode company1 = Json.parse(
						"{\"id\": 1, \"name\": \"Test Bank-1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(),
						OK);

				JsonNode company2 = Json.parse(
						"{\"id\": 2, \"name\": \"Test Bank-2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 80}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(),
						OK);

				JsonNode identification = Json.parse(
						"{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
						.getStatus(), OK);

				JsonNode identification2 = Json.parse(
						"{\"id\": 2, \"name\": \"Ralf Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000)
						.getStatus(), OK);

				JsonNode pendingIdentifications = WS.url("http://localhost:3333/api/v1/identifications").get()
						.get(10000).asJson();

				assertEquals(getDataByIndex(pendingIdentifications, 0).get("id").asInt(), 1);
				assertEquals(getDataByIndex(pendingIdentifications, 1).get("id").asInt(), 2);
			}

		
		});
	}
	
	private JsonNode getDataByIndex(JsonNode list, int index) {
		if(list.isArray()){
			for(JsonNode item: list){
				if(item.get("index").asInt() == index){
					System.out.println(item);
					return item;
				}
			}						
		}
		return null;
	}
}
