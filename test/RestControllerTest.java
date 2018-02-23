import java.util.Map;

import static org.junit.Assert.assertEquals;

import play.mvc.Http;
import static play.mvc.Http.Status.OK;
import play.mvc.Result;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.BeforeClass;
import org.junit.Test;

import play.api.test.FakeApplication;
import play.api.test.Helpers;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolution;
import play.db.evolutions.Evolutions;
import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.routes;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

			
	@Test
	public void postIdentification() {
		running(testServer(3000, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3000/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				assertEquals(WS.url("http://localhost:3000/api/v1/identifications").get().get(10000).getStatus(), OK);
				
				
			}
		});

	}
	
	// Test with Eample 1 given in readme
	@Test
	public void getIdentificationsTest1() {
		running(testServer(3000, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3000/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				identification = Json.parse("{\"id\": 2, \"name\": \"Ranjit Sinha\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				assertEquals(WS.url("http://localhost:3000/api/v1/identifications").get().get(10000).getStatus(), OK);
				
			}
		});

	}
	
	//// Test with Eample 2 given in readme
	@Test
	public void getIdentificationsTest2() {
		running(testServer(3000, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3000/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				company = Json.parse("{\"id\": 2, \"name\": \"Test Bank1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				assertEquals(WS.url("http://localhost:3000/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				identification = Json.parse("{\"id\": 2, \"name\": \"Peter Huber1\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				assertEquals(WS.url("http://localhost:3000/api/v1/identifications").get().get(10000).getStatus(), OK);
				
			}
		});

	}
	
	// Test with Eample 3 given in readme
	@Test
	public void getIdentificationsTest3() {
		running(testServer(3000, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3000/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				company = Json.parse("{\"id\": 2, \"name\": \"Test Bank1\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3000/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				identification = Json.parse("{\"id\": 2, \"name\": \"Peter Huber1\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				assertEquals(WS.url("http://localhost:3000/api/v1/identifications").get().get(10000).getStatus(), OK);
				
			}
		});

	}
	
	// Test with Eample 4 given in readme
	@Test
	public void getIdentificationsTest4() {
		running(testServer(3000, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3000/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				company = Json.parse("{\"id\": 2, \"name\": \"Test Bank1\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				assertEquals(WS.url("http://localhost:3000/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				identification = Json.parse("{\"id\": 2, \"name\": \"Peter Huber1\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3000/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				assertEquals(WS.url("http://localhost:3000/api/v1/identifications").get().get(10000).getStatus(), OK);
				
			}
		});

	}

	
		
	
}
