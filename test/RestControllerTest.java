import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Simple (JUnit) tests that can call all parts of a play app. If you are interested in mocking a
 * whole application, see the wiki for more details.
 */
public class RestControllerTest {

  JsonNode identifications;

  @Test
  public void getIdentifications() {
    running(
        testServer(3333, fakeApplication(inMemoryDatabase())),
        new Runnable() {
          @Override
          public void run() {
            assertEquals(
                WS.url("http://localhost:3333/api/v1/pendingIdentifications")
                    .get()
                    .get(10000)
                    .getStatus(),
                OK);
          }
        });
  }

  @Test
  public void postIdentification() {
    running(
        testServer(3333, fakeApplication(inMemoryDatabase())),
        new Runnable() {
          @Override
          public void run() {
            JsonNode company =
                Json.parse(
                    "{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(
                WS.url("http://localhost:3333/api/v1/addCompany")
                    .post(company)
                    .get(10000)
                    .getStatus(),
                OK);

            JsonNode identification =
                Json.parse(
                    "{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
            assertEquals(
                WS.url("http://localhost:3333/api/v1/startIdentification")
                    .post(identification)
                    .get(10000)
                    .getStatus(),
                OK);
          }
        });
  }

  @Test
  public void testOrderingOfIdentifications() {
    running(
        testServer(3333, fakeApplication(inMemoryDatabase())),
        new Runnable() {
          @Override
          public void run() {
            // Adding companies via REST API should be successful.
            JsonNode company1 =
                Json.parse(
                    "{\"id\": 1, \"name\": \"ABC\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            JsonNode company2 =
                Json.parse(
                    "{\"id\": 2, \"name\": \"DEF\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
            assertEquals(
                WS.url("http://localhost:3333/api/v1/addCompany")
                    .post(company1)
                    .get(10000)
                    .getStatus(),
                OK);

            assertEquals(
                WS.url("http://localhost:3333/api/v1/addCompany")
                    .post(company2)
                    .get(10000)
                    .getStatus(),
                OK);

            // Adding identifications via REST API should be successful.
            JsonNode identification1 =
                Json.parse(
                    "{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
            JsonNode identification2 =
                Json.parse(
                    "{\"id\": 2, \"name\": \"Roger Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
            assertEquals(
                WS.url("http://localhost:3333/api/v1/startIdentification")
                    .post(identification1)
                    .get(10000)
                    .getStatus(),
                OK);

            assertEquals(
                WS.url("http://localhost:3333/api/v1/startIdentification")
                    .post(identification2)
                    .get(10000)
                    .getStatus(),
                OK);

            // Verify the identification with higher priority is ordered properly.
            // identification1 (id = 1) has higher priority and thus should be presented as the 1st
            // element when we request the pending identifications.
            JsonNode items =
                WS.url("http://localhost:3333/api/v1/pendingIdentifications")
                    .get()
                    .get(10000)
                    .asJson();
            assertEquals("1", items.get(0).findValues("id").get(0).asText());
          }
        });
  }
}
