import static org.junit.Assert.*;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Before;
import org.junit.Test;

import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;

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
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
			}
		});

	}

	@Before
	public void init() {
		identifications = Json.newArray();
	}

	@Test
	public void inputTestCompany_wrongFormatJson() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post("").get(10000).getStatus(), BAD_REQUEST);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post("{\"company_id\": 1}").get(10000).getStatus(), BAD_REQUEST);
			}
		});
	}

	@Test
	public void inputTestIdentification_wrongFormatJson() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post("").get(10000).getStatus(), BAD_REQUEST);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post("{\"ident_id\": 1}").get(10000).getStatus(), BAD_REQUEST);
			}
		});
	}

	@Test
	public void orderingTest_SameCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Peter Huber 2\", \"time\": 1435667215, \"waiting_time\": 20, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

				JsonNode identification3 = Json.parse("{\"id\": 3, \"name\": \"Peter Huber 3\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification3).get(10000).getStatus(), OK);

				JsonNode arr = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
				assertNotEquals(arr.size(), 0);
				assertEquals(arr.get(0), identification3); //the first element is expected to be id 3
				assertEquals(arr.get(1), identification2);
				assertEquals(arr.get(2), identification1);
			}
		});

	}

	@Test
	public void orderingTest_MoreUrgent_SLA() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.9}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Peter Huber 2\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

				JsonNode arr = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();

				assertEquals(arr.get(0), identification2);
				assertEquals(arr.get(1), identification1);
			}
		});

	}

	@Test
	public void orderingTest_MoreUrgent_SLATime() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.85}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Peter Huber 2\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

				JsonNode arr = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();

				assertEquals(arr.get(0), identification1);
				assertEquals(arr.get(1), identification2);
			}
		});

	}

	@Test
	public void orderingTest_Mixed() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 40, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Peter Huber 2\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

				JsonNode arr = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();

				assertEquals(arr.get(0), identification1);
				assertEquals(arr.get(1), identification2);
			}
		});

	}
}
