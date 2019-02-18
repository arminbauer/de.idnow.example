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
 * Company controller tests
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class CompanyControllerTest {

    @Test
    public void testAddNewCompanySuccess() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
        });
    }


    @Test
    public void testAddNewCompanyWithoutId() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company = Json.parse("\"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), BAD_REQUEST);
        });
    }

    @Test
    public void testAddNewCompanyWithNegativeNumbers() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": -60, \"sla_percentage\": -0.9, \"current_sla_percentage\": -0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), BAD_REQUEST);

            JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank\", \"sla_time\": -60, \"sla_percentage\": -0.9, \"current_sla_percentage\": -0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getBody(),
                    "Company sla time should be greater than zero! " +
                            "Company sla percentage should be greater than zero! " +
                            "Company current sla percentage can't be negative!");
        });
    }

}
