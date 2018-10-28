import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WSResponse;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	@Test
	public void getIdentificationsExample1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000);

				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Peter Second\", \"time\": 1435667225, \"waiting_time\": 45, \"companyid\": 1}");
				WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000);


				WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(wsResponse.getStatus(), OK);
				assertEquals(wsResponse.asJson(), Json.newArray().add(identification2).add(identification1));
			}
		});

	}

	@Test
	public void getIdentificationsExample2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 120, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.9}");
				WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000);

				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Peter Second\", \"time\": 1435667225, \"waiting_time\": 30, \"companyid\": 2}");
				WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000);


				WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(wsResponse.getStatus(), OK);
				assertEquals(wsResponse.asJson(), Json.newArray().add(identification2).add(identification1));
			}
		});
	}


	@Test
	public void getIdentificationsExample3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000);

				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 60, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000);

				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000);

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Peter Second\", \"time\": 1435667225, \"waiting_time\": 30, \"companyid\": 2}");
				WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000);


				WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(wsResponse.getStatus(), OK);
				assertEquals(wsResponse.asJson(), Json.newArray().add(identification1).add(identification2));
			}
		});

	}

	@Test
	public void postIdentificationSuccessful() {
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

	@Test
	public void postCompanySuccessful() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
			}
		});

	}

	@Test
	public void postCompanyNoSlaPercentage() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

	@Test
	public void postIdentificationNoCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

	@Test
	public void postCompanyBadRequest() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"strange_field\": 60}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

	@Test
	public void postIdentificationBadRequest() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"strange_field\": 60}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

	@Test
	public void postCompanySuccessful() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
			}
		});

	}

	@Test
	public void postCompanyNoSlaPercentage() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

	@Test
	public void postIdentificationNoCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

	@Test
	public void postCompanyBadRequest() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"strange_field\": 60}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

	@Test
	public void postIdentificationBadRequest() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"strange_field\": 60}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

}
