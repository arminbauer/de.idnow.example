import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
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
                JsonNode company = Json.parse("{\"id\": \"123e4567-e89b-42d3-a456-556642440000\", \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus());

                JsonNode identification = Json.parse("{\"id\": \"123e4568-e89b-42d3-a456-556642440000\", \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyId\": \"123e4567-e89b-42d3-a456-556642440000\"}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus());
            }
        });

    }

}
