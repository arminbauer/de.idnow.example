import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.inMemoryDatabase;

public class RestControllersTest {

    @Test
    public void getIdentifications() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

                JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Peter Maier\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

                JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();

                Iterator<JsonNode> elements =jsonNode.elements();
                JsonNode firstNode = elements.next();
                assertEquals(firstNode.get("waiting_time").asText(), "45");

            }
        });

    }

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
}
