import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.libs.Json;
import play.libs.ws.WS;
import play.twirl.api.Content;
import static play.test.Helpers.*;
import static org.junit.Assert.*;
/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {
	private final String APP_URL="http://localhost:3333/api/v1/";
	private final String IDENTIFICATIONS_URL=APP_URL+"identifications";
	private final String PENDING_IDENTIFICATIONS_URL=APP_URL+"pendingIdentifications";
	private final String START_IDENTIFICATIONS_URL=APP_URL+"startIdentification";
	private final String ADD_COMPANY_URL=APP_URL+"addCompany";
	JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
	JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Alan Jackson\", \"time\": 1435667215, \"waiting_time\": 20, \"companyid\": 1}");
	JsonNode identification3 = Json.parse("{\"id\": 3, \"name\": \"Clark Ken\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
	JsonNode identification4 = Json.parse("{\"id\": 4, \"name\": \"Peter Pan\", \"time\": 1435667215, \"waiting_time\": 40, \"companyid\": 2}");
	JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Local Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
	JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Global Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.8}");
	
	@Test
	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url(IDENTIFICATIONS_URL).get().get(10000).getStatus(), OK);
			}
		});
	}
	
	

	@Test
	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url(ADD_COMPANY_URL).post(company1).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(identification1).get(10000).getStatus(), OK);
			}
		});

	}
	
	@Test
	public void postIdentificationWithoutCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(identification1).get(10000).getStatus(), 400);
			}
		});
	}
	
	@Test
	public void testPendingIdentificationsURL() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url(PENDING_IDENTIFICATIONS_URL).get().get(10000).getStatus(), OK);
			}
		});
	}
	
	@Test
	public void testNoDuplicatedIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {	
				assertEquals(WS.url(ADD_COMPANY_URL).post(company1).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(identification1).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(identification1).get(10000).getStatus(), OK);
				Promise<JsonNode> jsonPromise = WS.url(PENDING_IDENTIFICATIONS_URL).get().map(response -> {
				    return response.asJson();
				});
				JsonNode idens = jsonPromise.get(10000);
				assertTrue(idens.isArray());
				assertEquals(idens.size(),1);
			}
		});
	}
	
	@Test
	public void testIdentificationOrder() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {	
				assertEquals(WS.url(ADD_COMPANY_URL).post(company1).get(10000).getStatus(), OK);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company2).get(10000).getStatus(), OK);				
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(identification1).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(identification2).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(identification3).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(identification4).get(10000).getStatus(), OK);
				Promise<JsonNode> jsonPromise = WS.url(PENDING_IDENTIFICATIONS_URL).get().map(response -> {
				    return response.asJson();
				});
				JsonNode idens = jsonPromise.get(10000);
				assertTrue(idens.isArray());
				assertEquals(idens.size(),4);
				assertEquals(idens.get(0).findPath("id").asInt(),4);
				assertEquals(idens.get(1).findPath("id").asInt(),3);
				assertEquals(idens.get(2).findPath("id").asInt(),2);
				assertEquals(idens.get(3).findPath("id").asInt(),1);
			}
		});
	}

	@Test
	public void testWithExample1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {	
				
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Local Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode iden1 = Json.parse("{\"id\": 1, \"name\": \"AAAA\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				JsonNode iden2 = Json.parse("{\"id\": 2, \"name\": \"BBBB\", \"time\": 1435667215, \"waiting_time\": 40, \"companyid\": 1}");
				assertEquals(WS.url(ADD_COMPANY_URL).post(company).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(iden1).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(iden2).get(10000).getStatus(), OK);
				Promise<JsonNode> jsonPromise = WS.url(PENDING_IDENTIFICATIONS_URL).get().map(response -> {
				    return response.asJson();
				});
				JsonNode idens = jsonPromise.get(10000);
				assertTrue(idens.isArray());
				assertEquals(idens.size(),2);
				assertEquals(idens.get(0).findPath("id").asInt(),2);
				assertEquals(idens.get(1).findPath("id").asInt(),1);
			}
		});
	}
	
	@Test
	public void testWithExample2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {	
				
				JsonNode com1 = Json.parse("{\"id\": 1, \"name\": \"Test Local Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode com2 = Json.parse("{\"id\": 2, \"name\": \"Test Local Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				JsonNode iden1 = Json.parse("{\"id\": 1, \"name\": \"AAAA\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode iden2 = Json.parse("{\"id\": 2, \"name\": \"BBBB\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url(ADD_COMPANY_URL).post(com1).get(10000).getStatus(), OK);
				assertEquals(WS.url(ADD_COMPANY_URL).post(com2).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(iden1).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(iden2).get(10000).getStatus(), OK);
				Promise<JsonNode> jsonPromise = WS.url(PENDING_IDENTIFICATIONS_URL).get().map(response -> {
				    return response.asJson();
				});
				JsonNode idens = jsonPromise.get(10000);
				assertTrue(idens.isArray());
				assertEquals(idens.size(),2);
				assertEquals(idens.get(0).findPath("id").asInt(),2);
				assertEquals(idens.get(1).findPath("id").asInt(),1);
			}
		});
	}
	
	@Test
	public void testWithExample3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {	
				
				JsonNode com1 = Json.parse("{\"id\": 1, \"name\": \"Test Local Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode com2 = Json.parse("{\"id\": 2, \"name\": \"Test Local Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				JsonNode iden1 = Json.parse("{\"id\": 1, \"name\": \"AAAA\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode iden2 = Json.parse("{\"id\": 2, \"name\": \"BBBB\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url(ADD_COMPANY_URL).post(com1).get(10000).getStatus(), OK);
				assertEquals(WS.url(ADD_COMPANY_URL).post(com2).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(iden1).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(iden2).get(10000).getStatus(), OK);
				Promise<JsonNode> jsonPromise = WS.url(PENDING_IDENTIFICATIONS_URL).get().map(response -> {
				    return response.asJson();
				});
				JsonNode idens = jsonPromise.get(10000);
				assertTrue(idens.isArray());
				assertEquals(idens.size(),2);
				assertEquals(idens.get(0).findPath("id").asInt(),1);
				assertEquals(idens.get(1).findPath("id").asInt(),2);
			}
		});
	}
	
	@Test
	public void testWithExample4() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {	
				
				JsonNode com1 = Json.parse("{\"id\": 1, \"name\": \"Test Local Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode com2 = Json.parse("{\"id\": 2, \"name\": \"Test Local Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.8}");
				JsonNode iden1 = Json.parse("{\"id\": 1, \"name\": \"AAAA\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				JsonNode iden2 = Json.parse("{\"id\": 2, \"name\": \"BBBB\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url(ADD_COMPANY_URL).post(com1).get(10000).getStatus(), OK);
				assertEquals(WS.url(ADD_COMPANY_URL).post(com2).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(iden1).get(10000).getStatus(), OK);
				assertEquals(WS.url(START_IDENTIFICATIONS_URL).post(iden2).get(10000).getStatus(), OK);
				Promise<JsonNode> jsonPromise = WS.url(PENDING_IDENTIFICATIONS_URL).get().map(response -> {
				    return response.asJson();
				});
				JsonNode idens = jsonPromise.get(10000);
				assertTrue(idens.isArray());
				assertEquals(idens.size(),2);
				assertEquals(idens.get(0).findPath("id").asInt(),1);
				assertEquals(idens.get(1).findPath("id").asInt(),2);
			}
		});
	}
}
