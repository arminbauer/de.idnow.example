import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import models.Company;
import models.Identification;
import org.junit.Test;

import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WSResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	private final static ObjectMapper MAPPER = new ObjectMapper();
	private final static LocalDateTime TIME = LocalDateTime.now();

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
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus());

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus());
			}
		});
	}

	@Test
	public void postIdentificationWithNullCompanyIdReturns400() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": null}");
			WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000);
			assertEquals(BAD_REQUEST, response.getStatus());
			assertEquals("Identification companyId should be defined.", response.getBody());
		});
	}

	@Test
	public void postIdentificationWithWrongCompanyIdReturns400() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
			WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000);
			assertEquals(BAD_REQUEST, response.getStatus());
			assertEquals("Company with id 1 is not found.", response.getBody());
		});
	}

	@Test
	public void postCompanyWorksCorrectly() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus());
		});
	}

	@Test
	public void postWithWrongJSONReturns400() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode identification = Json.parse("{\"id\": \"one\", \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
			WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000);
			assertEquals(BAD_REQUEST, response.getStatus());
			assertEquals("Parsing json failed. Reason:", response.getBody().substring(0, 28));
		});
	}

	@Test
	public void postWithoutJSONReturns400() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			JsonNode identification = null;
			WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000);
			assertEquals(BAD_REQUEST, response.getStatus());
			assertEquals("Json is not found in request.", response.getBody());
		});
	}

	@Test
	public void getIdentificationsWorksCorrectly1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			// Populate db with required data
			Company company = new Company("Test company 1", 60, 0.9F, 0.95F);
			company.save();
			Identification identification1 = new Identification("Test name 1", TIME, 30, 1L);
			identification1.save();
			Identification identification2 = new Identification("Test name 2", TIME, 45, 1L);
			identification2.save();
			// Prepare expected result
			List<String> expectedOrder = Lists.newArrayList(identification2.name, identification1.name);
			// Get actual result
			List<String> actualOrder = getActualOrder();
			// Compare results
			assertEquals(expectedOrder, actualOrder);
		});
	}

	@Test
	public void getIdentificationsWorksCorrectly2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			// Populate db with required data
			Company company1 = new Company("Test company 1", 60, 0.9F, 0.95F);
			company1.save();
			Company company2 = new Company("Test company 2", 60, 0.9F, 0.90F);
			company2.save();
			Identification identification1 = new Identification("Test name 1", TIME, 30, 1L);
			identification1.save();
			Identification identification2 = new Identification("Test name 2", TIME, 30, 2L);
			identification2.save();
			// Prepare expected result
			List<String> expectedOrder = Lists.newArrayList(identification2.name, identification1.name);
			// Get actual result
			List<String> actualOrder = getActualOrder();
			// Compare results
			assertEquals(expectedOrder, actualOrder);
		});
	}

	@Test
	public void getIdentificationsWorksCorrectly3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			// Populate db with required data
			Company company1 = new Company("Test company 1", 60, 0.9F, 0.95F);
			company1.save();
			Company company2 = new Company("Test company 2", 120, 0.8F, 0.95F);
			company2.save();
			Identification identification1 = new Identification("Test name 1", TIME, 30, 1L);
			identification1.save();
			Identification identification2 = new Identification("Test name 2", TIME, 30, 2L);
			identification2.save();
			// Prepare expected result
			List<String> expectedOrder = Lists.newArrayList(identification1.name, identification2.name);
			// Get actual result
			List<String> actualOrder = getActualOrder();
			// Compare results
			assertEquals(expectedOrder, actualOrder);
		});
	}

	@Test
	public void getIdentificationsWorksCorrectly4() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			// Populate db with required data
			Company company1 = new Company("Test company 1", 60, 0.9F, 0.95F);
			company1.save();
			Company company2 = new Company("Test company 2", 120, 0.8F, 0.80F);
			company2.save();
			Identification identification1 = new Identification("Test name 1", TIME, 45, 1L);
			identification1.save();
			Identification identification2 = new Identification("Test name 2", TIME, 30, 2L);
			identification2.save();
			// Prepare expected result
			List<String> expectedOrder = Lists.newArrayList(identification1.name, identification2.name);
			// Get actual result
			List<String> actualOrder = getActualOrder();
			// Compare results
			assertEquals(expectedOrder, actualOrder);
		});
	}

	private List<String> getActualOrder() {
		// Invoke the endpoint which we check
		String body = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getBody();
		// Deserialize json response to list of main
		List<Identification> identifications = new ArrayList<>();
		try {
			identifications = MAPPER.readValue(body, new TypeReference<List<Identification>>() {});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// Map list of main to list of identification names for result comparision
		return identifications.stream().map(it -> it.name).collect(Collectors.toList());
	}
}
