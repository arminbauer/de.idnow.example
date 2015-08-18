import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.*;

import play.libs.Json;
import play.libs.ws.WS;
import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertTrue(browser.pageSource().contains("Your new application is ready."));
            }
        });
    }

    @Test
    public void testIdentificationsFull() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company1 = Json.parse("{\"id\": 201, \"name\": \"Test Bank 1\", \"sla_time\": 2, \"sla_percentage\": 0.7, \"current_sla_percentage\": 0.1}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus());

                JsonNode i1_1 = Json.parse("{\"id\": 101, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 201}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(i1_1).get(10000).getStatus());

                JsonNode i1_2 = Json.parse("{\"id\": 102, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 100, \"companyid\": 201}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(i1_2).get(10000).getStatus());


                JsonNode company2 = Json.parse("{\"id\": 202, \"name\": \"Test Bank 2\", \"sla_time\": 1, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.99}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus());

                JsonNode i2_1 = Json.parse("{\"id\": 201, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 202}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(i2_1).get(10000).getStatus());

                JsonNode i2_2 = Json.parse("{\"id\": 202, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 100, \"companyid\": 202}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(i2_2).get(10000).getStatus());

                ArrayNode items = (ArrayNode) WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
                assertTrue(items.size() >= 4);
                assertEquals("202", items.get(0).get("id").asText());
                assertEquals("201", items.get(1).get("id").asText());
                assertEquals("102", items.get(2).get("id").asText());
                assertEquals("101", items.get(3).get("id").asText());
            }
        });
    }

}
