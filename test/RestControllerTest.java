import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import org.scalatools.testing.Logger;
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
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus());
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

				JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
				assertEquals(1, jsonNode.size());
			}
		});

	}

    @Test
    public void getIdentifications_order_correct() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank A\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000);

                company = Json.parse("{\"id\": 2, \"name\": \"Test Bank B\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
                WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000);

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
                WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus();

                identification = Json.parse("{\"id\": 3, \"name\": \"Peter A\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 2}");
                WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000);

                identification = Json.parse("{\"id\": 4, \"name\": \"Peter B\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
                WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000);

                JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
                assertTrue(jsonNode.isArray());
                assertEquals(3, jsonNode.size());
             }
        });

    }

}
