import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.mvc.Http.Status.CREATED;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
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
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            Integer companyId = createCompany().get("id").asInt();

            WSResponse response = createIdentification(companyId);

            assertEquals(response.getStatus(), CREATED);
            assertNotNull(response.asJson().get("id"));
        });
    }

    private WSResponse createIdentification(Integer companyId) {
        JsonNode identification = Json.parse("{\"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": " + companyId + "}");

        return WS.url("http://localhost:3333/api/v1/identifications").post(identification).get(10000);
    }

    private JsonNode createCompany() {
        JsonNode company = Json.parse("{\"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/companies").post(company).get(10000);
        assertEquals(wsResponse.getStatus(), CREATED);
        return wsResponse.asJson();
    }
}
