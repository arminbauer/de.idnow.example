import models.CompanyStore;
import models.IdentificationStore;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {
	@Test
	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () ->
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus()));
	}

    @Test
    public void postIdentification() {
	    CompanyStore.clear();
	    IdentificationStore.clear();

		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			UUID companyId = UUID.randomUUID();
			String companyJson = String.format("{\"id\": \"%s\", \"name\": \"Test Bank\", \"sla_time\": 60, " +
					                                   "\"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}",
			                                   companyId);
			JsonNode company = Json.parse(companyJson);

			int companyStatus = WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus();
			assertEquals(OK, companyStatus);

			String identificationJson
					= String.format("{\"id\": \"%s\", \"name\": \"User1\", \"time\": %d, " +
							                "\"waiting_time\": 10, \"company_id\": \"%s\"}",
                                    UUID.randomUUID().toString(),
                                    LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond(),
                                    companyId);
			JsonNode identification = Json.parse(identificationJson);

			int identificationStatus
					= WS.url("http://localhost:3333/api/v1/startIdentification").post(identification)
					    .get(10000).getStatus();
			assertEquals(OK, identificationStatus);
		});
	}
}
