package controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Identification controller test cases
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationControllerTest {

    @Test
    public void postIdentification() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

            JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
        });
    }

    @Test
    public void postIdentificationWithIncorrectValues() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

            JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": -10, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), BAD_REQUEST);

            JsonNode identification2 = Json.parse("{\"id\": 0, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": -10, \"companyid\": 0}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getBody(),
                    "Identification id should be greater than zero! " +
                            "Identification waiting time should be greater than zero! " +
                            "Company id should be greater than zero!");
        });
    }
}
