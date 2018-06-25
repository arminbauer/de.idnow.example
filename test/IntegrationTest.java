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
            }
        });
    }

    @Test
    public void testIdentificationsFull() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode c1 = Json.parse("{\"id\": 1, \"name\": \"Company1\", \"sla_time\": 60, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.85}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(c1).get(10000).getStatus());

                JsonNode idn01 = Json.parse("{\"id\": 71, \"name\": \"Mohit\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(idn01).get(10000).getStatus());

                JsonNode idn02 = Json.parse("{\"id\": 72, \"name\": \"Rohit\", \"time\": 1435667215, \"waiting_time\": 100, \"companyid\": 1}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(idn02).get(10000).getStatus());


                JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Company2\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.99}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus());

                JsonNode idn11 = Json.parse("{\"id\": 91, \"name\": \"Neha\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(idn11).get(10000).getStatus());

                JsonNode idn12 = Json.parse("{\"id\": 92, \"name\": \"Sia\", \"time\": 1435667215, \"waiting_time\": 100, \"companyid\": 2}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(idn12).get(10000).getStatus());

                ArrayNode items = (ArrayNode) WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
            }
        });
    }
}
