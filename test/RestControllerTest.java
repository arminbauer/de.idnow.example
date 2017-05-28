import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

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
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus());

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus());
			}
		});
	}

	@Test
	public void it_fails_to_create_two_companies_with_same_id() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus());

				JsonNode company2 = Json.parse("{\"id\": 1, \"name\": \"Some other Test Bank\", \"sla_time\": 120, \"sla_percentage\": 0.95, \"current_sla_percentage\": 0.95}");
				assertEquals(422, WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus());
			}
		});
	}

	@Test
	public void it_passes_to_create_two_companies_with_an_identification_each_and_sort_them_correct() {
    // This is example 3
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus());

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Some other Test Bank\", \"sla_time\": 90, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus());

				JsonNode identification1 = Json.parse("{\"id\": 1000, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus());

				JsonNode identification2 = Json.parse("{\"id\": 1001, \"name\": \"Inge Huber\", \"time\": 1435667789, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus());

        assertEquals(OK, WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus());

        JsonNode response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
        assertEquals(1000, response.get(0).get("id").asInt());
        assertEquals(1001, response.get(1).get("id").asInt());
			}
		});
	}

}
