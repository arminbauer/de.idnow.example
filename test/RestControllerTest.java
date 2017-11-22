import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import models.identification.jsons.IdentificationJson;
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

				assertEquals(WS.url("http://localhost:3333/api/test/reset").delete().get(10000).getStatus(), OK);

				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
			}
		});

	}


	static class Pos_Wrapper {
		int pos;
	}

	@Test
	public void testCase1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/test/reset").delete().get(10000).getStatus(), OK);


				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);


				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Nguyen\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);


				JsonNode node = WS.url("http://localhost:3333/api/v1/identifications").get().get(1000).asJson();
				final Pos_Wrapper posWrapper = new Pos_Wrapper();
				node.elements().forEachRemaining(jsonNode -> {

					IdentificationJson item = Json.fromJson(jsonNode, IdentificationJson.class);
					if (posWrapper.pos == 0){
						assertEquals(item.id, 2);
					} else {
						assertEquals(item.id, 1);
					}
					++posWrapper.pos;
				});

			}
		});

	}

	@Test
	public void testCase2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/test/reset").delete().get(10000).getStatus(), OK);

				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);


				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);


				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Nguyen\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);


				JsonNode node = WS.url("http://localhost:3333/api/v1/identifications").get().get(1000).asJson();
				final Pos_Wrapper posWrapper = new Pos_Wrapper();
				node.elements().forEachRemaining(jsonNode -> {

					IdentificationJson item = Json.fromJson(jsonNode, IdentificationJson.class);
					if (posWrapper.pos == 0){
						assertEquals(item.id, 2);
					} else {
						assertEquals(item.id, 1);
					}
					++posWrapper.pos;
				});

			}
		});

	}



	@Test
	public void testCase3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/test/reset").delete().get(10000).getStatus(), OK);

				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);


				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);


				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Nguyen\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);


				JsonNode node = WS.url("http://localhost:3333/api/v1/identifications").get().get(1000).asJson();
				final Pos_Wrapper posWrapper = new Pos_Wrapper();
				node.elements().forEachRemaining(jsonNode -> {

					IdentificationJson item = Json.fromJson(jsonNode, IdentificationJson.class);
					if (posWrapper.pos == 0){
						assertEquals(item.id, 1);
					} else {
						assertEquals(item.id, 2);
					}
					++posWrapper.pos;
				});

			}
		});



	}


	@Test
	public void testCase4() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/test/reset").delete().get(10000).getStatus(), OK);

				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);


				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);


				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Nguyen\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);


				JsonNode node = WS.url("http://localhost:3333/api/v1/identifications").get().get(1000).asJson();
				final Pos_Wrapper posWrapper = new Pos_Wrapper();
				node.elements().forEachRemaining(jsonNode -> {

					IdentificationJson item = Json.fromJson(jsonNode, IdentificationJson.class);
					if (posWrapper.pos == 0){
						assertEquals(item.id, 1);
					} else {
						assertEquals(item.id, 2);
					}
					++posWrapper.pos;
				});

			}
		});



	}

	@Test
	public void testCase5() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/test/reset").delete().get(10000).getStatus(), OK);

				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);


				JsonNode company2 = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), BAD_REQUEST);
			}

		});



	}

	@Test
	public void testCase6() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"John Nguyen\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), BAD_REQUEST);


			}
		});



	}

	@Test
	public void testCase7() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				assertEquals(WS.url("http://localhost:3333/api/test/reset").delete().get(10000).getStatus(), OK);


				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);


				JsonNode identification2 = Json.parse("{\"id\": 1, \"name\": \"John Nguyen\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}



}
