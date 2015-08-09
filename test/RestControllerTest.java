import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;

import org.junit.Test;

import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;
import play.twirl.api.Content;
import util.DBEmulator;
import util.IdentificationService;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	JsonNode identifications;

	@Test
	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus(), OK);
			}
		});

	}

	@Test
	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSlaPercentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"waitingTime\": 10, \"companyId\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
			}
		});
	}

	@Test
	public void renderTemplate_test1() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/test1").get().get(10000).getStatus(), OK);

				assertTrue(WS.url("http://localhost:3333/test1").get().get(10000).getBody().contains("Solution:"));
				assertTrue(WS.url("http://localhost:3333/test1").get().get(10000).getBody().contains("1 - Identification 2"));
				assertTrue(WS.url("http://localhost:3333/test1").get().get(10000).getBody().contains("2 - Identification 1"));

			}
		});
	}

	@Test
	public void renderTemplate_test2() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/test2").get().get(10000).getStatus(), OK);

				assertTrue(WS.url("http://localhost:3333/test2").get().get(10000).getBody().contains("Solution:"));
				assertTrue(WS.url("http://localhost:3333/test2").get().get(10000).getBody().contains("1 - Identification 2"));
				assertTrue(WS.url("http://localhost:3333/test2").get().get(10000).getBody().contains("2 - Identification 1"));

			}
		});
	}

	@Test
	public void renderTemplate_test3() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/test3").get().get(10000).getStatus(), OK);

				assertTrue(WS.url("http://localhost:3333/test3").get().get(10000).getBody().contains("Solution:"));
				assertTrue(WS.url("http://localhost:3333/test3").get().get(10000).getBody().contains("1 - Identification 1"));
				assertTrue(WS.url("http://localhost:3333/test3").get().get(10000).getBody().contains("2 - Identification 2"));

			}
		});
	}

	@Test
	public void renderTemplate_test4() {

		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/test4").get().get(10000).getStatus(), OK);

				assertTrue(WS.url("http://localhost:3333/test4").get().get(10000).getBody().contains("Solution:"));
				assertTrue(WS.url("http://localhost:3333/test4").get().get(10000).getBody().contains("1 - Gigi"));
				assertTrue(WS.url("http://localhost:3333/test4").get().get(10000).getBody().contains("2 - John"));

			}
		});
	}

}
