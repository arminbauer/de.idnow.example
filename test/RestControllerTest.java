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
				JsonNode companyOne = Json.parse("{\"id\": 1, \"name\": \"Company One\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(companyOne).get(10000).getStatus());

				JsonNode companyTwo = Json.parse("{\"id\": 2, \"name\": \"Company Two\", \"sla_time\": 120, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.85}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(companyTwo).get(10000).getStatus());

				JsonNode identificationOne = Json.parse("{\"id\": 1, \"name\": \"Ident One\", \"time\": 1552845098, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationOne).get(10000).getStatus());

				JsonNode identificationTwo = Json.parse("{\"id\": 2, \"name\": \"Ident Two\", \"time\": 1552845098, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationTwo).get(10000).getStatus());

				JsonNode identificationThree = Json.parse("{\"id\": 3, \"name\": \"Ident Three\", \"time\": 1552845098, \"waiting_time\": 40, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationThree).get(10000).getStatus());

				JsonNode identificationFour = Json.parse("{\"id\": 4, \"name\": \"Ident Four\", \"time\": 1552845098, \"waiting_time\": 50, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identificationFour).get(10000).getStatus());

				JsonNode items = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
				assertEquals("[4]", items.get(0).get(0).findValues("id").toString());
				assertEquals("[3]", items.get(0).get(1).findValues("id").toString());
				assertEquals("[2]", items.get(1).get(0).findValues("id").toString());
				assertEquals("[1]", items.get(1).get(1).findValues("id").toString());

			}
		});

	}

}
