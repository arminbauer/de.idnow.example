import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	final static Logger logger = LoggerFactory.getLogger(RestControllerTest.class);

	@Test
	public void createCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode companyNode = sendPostRequest("http://localhost:3333/api/v1/addCompany", company);
				assertEquals(companyNode.findPath("name").asText(), "Test Bank");
			}
		});
	}

	@Test
	public void createIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				// Create company
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode companyNode = sendPostRequest("http://localhost:3333/api/v1/addCompany", company);
				assertEquals(companyNode.findPath("name").asText(), "Test Bank");
				String companyId = company.findPath("name").asText();

				// Create identification
				JsonNode identification = Json.parse(String.format("{\"nameOfUser\": \"Test User\", \"companyId\": \"%s\", \"startTime\": 1468670686, \"waitTime\": 30}", companyId));
				JsonNode idNode = sendPostRequest("http://localhost:3333/api/v1/startIdentification", identification);
				assertEquals(idNode.findPath("nameOfUser").asText(), "Test User");

				// Get identifications
				ArrayNode body = (ArrayNode) sendGetRequest("http://localhost:3333/api/v1/identifications");
				assertEquals(body.get(0).findPath("nameOfUser").asText(), "Test User");
			}
		});
	}

	private JsonNode sendPostRequest(String url, JsonNode body) {
		CompletionStage<WSResponse> response = WS.url(url).post(body);
		try {
			WSResponse r = response.toCompletableFuture().get();
			logger.info(r.getBody());
			assertEquals(r.getStatus(), 200);
			JsonNode json = r.asJson();
			return json;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private JsonNode sendGetRequest(String url) {
		CompletionStage<WSResponse> response = WS.url(url).get();
		try {
			WSResponse r = response.toCompletableFuture().get();
			logger.info(r.getBody());
			assertEquals(r.getStatus(), 200);
			JsonNode json = r.asJson();
			return json;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
