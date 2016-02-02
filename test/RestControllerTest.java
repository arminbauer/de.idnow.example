import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import models.IdentificationRequest;
import org.junit.Test;

import play.api.data.Form;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WSResponse;

import java.util.Iterator;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class RestControllerTest {

    private JsonNode identifications;

    @Test
    public void getIdentifications() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            assertEquals(WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).getStatus(), OK);
        });
    }

    @Test
    public void postIdentification() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company = Json.parse("{\"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

            JsonNode identification = Json.parse("{\"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
        });
    }

    @Test
    public void testExample1() throws Exception {

        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company = Json.parse("{\"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

            JsonNode identification1 = Json.parse("{\"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

            JsonNode identification2 = Json.parse("{\"name\": \"Franz Schmidt\", \"time\": 1435668215, \"waiting_time\": 45, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

            JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();

            Iterator<JsonNode> elements = jsonNode.elements();
            JsonNode jsonNode1 = elements.next();
            assertEquals(jsonNode1.get("name").asText(), "Franz Schmidt");

            JsonNode jsonNode2 = elements.next();
            assertEquals(jsonNode2.get("name").asText(), "Peter Huber");
        });
    }

    @Test
    public void testExample2() throws Exception {

        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {

                JsonNode company1 = Json.parse("{\"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

                JsonNode company2 = Json.parse("{\"name\": \"Great Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

                JsonNode identification1 = Json.parse("{\"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

                JsonNode identification2 = Json.parse("{\"name\": \"Franz Schmidt\", \"time\": 1435668215, \"waiting_time\": 30, \"companyid\": 2}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

                JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();

                Iterator<JsonNode> elements = jsonNode.elements();
                JsonNode jsonNode1 = elements.next();
                assertEquals(jsonNode1.get("name").asText(), "Franz Schmidt");

                JsonNode jsonNode2 = elements.next();
                assertEquals(jsonNode2.get("name").asText(), "Peter Huber");
        });
    }

    @Test
    public void testExample3() throws Exception {

        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company1 = Json.parse("{\"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

            JsonNode company2 = Json.parse("{\"name\": \"Great Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

            JsonNode identification1 = Json.parse("{\"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

            JsonNode identification2 = Json.parse("{\"name\": \"Franz Schmidt\", \"time\": 1435668215, \"waiting_time\": 30, \"companyid\": 2}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

            JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();

            Iterator<JsonNode> elements = jsonNode.elements();
            JsonNode jsonNode1 = elements.next();
            assertEquals(jsonNode1.get("name").asText(), "Peter Huber");

            JsonNode jsonNode2 = elements.next();
            assertEquals(jsonNode2.get("name").asText(), "Franz Schmidt");
        });
    }

    @Test
    public void testExample4() throws Exception {

        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company1 = Json.parse("{\"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

            JsonNode company2 = Json.parse("{\"name\": \"Great Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.8}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

            JsonNode identification1 = Json.parse("{\"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

            JsonNode identification2 = Json.parse("{\"name\": \"Franz Schmidt\", \"time\": 1435668215, \"waiting_time\": 30, \"companyid\": 2}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

            JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();

            Iterator<JsonNode> elements = jsonNode.elements();
            JsonNode jsonNode1 = elements.next();
            assertEquals(jsonNode1.get("name").asText(), "Peter Huber");

            JsonNode jsonNode2 = elements.next();
            assertEquals(jsonNode2.get("name").asText(), "Franz Schmidt");
        });
    }
}
