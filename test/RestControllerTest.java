
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import java.io.IOException;
import java.util.List;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	public static final int TIMEOUT_TEN_SECOND = 10000;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void postInvalidCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode company1 = Json.newObject();
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(TIMEOUT_TEN_SECOND).getStatus(), BAD_REQUEST);
		});
	}

	@Test
	public void postEmptyCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode company1 = Json.parse("{\"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(TIMEOUT_TEN_SECOND).getStatus(), BAD_REQUEST);
		});
	}

	@Test
	public void postIdentNoCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"company_id\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(
					TIMEOUT_TEN_SECOND).getStatus(), NOT_FOUND);
		});
	}

	@Test
	public void postIdent() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(TIMEOUT_TEN_SECOND);

			JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"company_id\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(
					TIMEOUT_TEN_SECOND).getStatus(), OK);
		});
	}

	@Test
	public void listNoIdents() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(TIMEOUT_TEN_SECOND);
			assertThat(response.getStatus()).isEqualTo(OK);
			assertThat(response.getHeader("Content-Type")).isEqualTo("application/json; charset=utf-8");
			try {
				assertThat(objectMapper.readValue(response.getBody().getBytes(), List.class).size()).isEqualTo(0);
			} catch (IOException e) {
			}
		});
	}

	@Test
	public void listIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {

			// Companies
			JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(TIMEOUT_TEN_SECOND).getStatus(), OK);

			JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 70, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.94}");
			assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(TIMEOUT_TEN_SECOND).getStatus(), OK);

			// Identifications
			JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"company_id\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(
					TIMEOUT_TEN_SECOND).getStatus(), OK);

			JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Mark Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"company_id\": 1}");
			assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(
					TIMEOUT_TEN_SECOND).getStatus(), OK);


			WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(TIMEOUT_TEN_SECOND);
			assertThat(response.getStatus()).isEqualTo(OK);
			assertThat(response.getHeader("Content-Type")).isEqualTo("application/json; charset=utf-8");
			try {
				assertThat(objectMapper.readValue(response.getBody().getBytes(), List.class).size()).isEqualTo(2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
