import action.CompanyAction;
import action.IdentificationAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.After;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	//JsonNode identifications;

/*
	@Test
	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus(), OK);
			}
		});

	}
*/

	@After
	public void clearValues(){
		new CompanyAction().clearCompanies();
		new IdentificationAction().clearIdentifications();
	}

	@Test
	public void addCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
			}
		});

	}

	@Test
	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank2\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
			}
		});
	}

	@Test
	public void pendingIdentificationsScenario1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Bank of America\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Saint\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Doe\", \"time\": 1435667225, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus());

				JsonNode pendingIdentification = (ArrayNode) WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
				assertEquals("2", pendingIdentification.get(0).findValues("id").get(0).asText());
			}
		});
	}

	@Test
	public void pendingIdentificationsScenario2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Bank of London\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Bank of Germany\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Painter\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Deer\", \"time\": 1435667225, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus());

				JsonNode pendingIdentification = (ArrayNode) WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
				assertEquals("2", pendingIdentification.get(0).findValues("id").get(0).asText());
			}
		});
	}

	@Test
	public void pendingIdentificationsScenario3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Bank of Europe\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Bank of Scotland\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Painter\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Deer\", \"time\": 1435667225, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus());

				JsonNode pendingIdentification = (ArrayNode) WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
				assertEquals("1", pendingIdentification.get(0).findValues("id").get(0).asText());
			}
		});
	}

	@Test
	public void pendingIdentificationsScenario4() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Bank of Europe\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Bank of Scotland\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Painter\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Deer\", \"time\": 1435667225, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus());

				JsonNode pendingIdentification = (ArrayNode) WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
				assertEquals("1", pendingIdentification.get(0).findValues("id").get(0).asText());
			}
		});
	}

	@Test
	public void pendingIdentificationsScenario5() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Bank of Europe\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Bank of Scotland\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Painter\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Deer\", \"time\": 1435667225, \"waiting_time\": 100, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus());

				JsonNode identification3 = Json.parse("{\"id\": 3, \"name\": \"Sam Bridge\", \"time\": 1435667235, \"waiting_time\": 90, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification3).get(10000).getStatus());

				JsonNode pendingIdentification = (ArrayNode) WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
				assertEquals("2", pendingIdentification.get(0).findValues("id").get(0).asText());
			}
		});
	}

	@Test
	public void pendingIdentificationsScenario6() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Bank of London\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Bank of Germany\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Painter\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Deer\", \"time\": 1435667225, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus());

				JsonNode identification3 = Json.parse("{\"id\": 3, \"name\": \"Peter Dean\", \"time\": 1435667235, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification3).get(10000).getStatus(), OK);

				JsonNode pendingIdentification = (ArrayNode) WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
				assertEquals("3", pendingIdentification.get(0).findValues("id").get(0).asText());
			}
		});
	}
}
