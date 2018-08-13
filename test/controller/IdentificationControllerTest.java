package controller;

import static controller.TestHelper.*;
import static controller.TestHelper.ENDPOINT_IDENTIFICATION;
import static controller.TestHelper.ENDPOINT_START_IDENTIFICATION;
import static controller.TestHelper.JSON_COMPANY_1;
import static controller.TestHelper.JSON_EXAMPLE_1_COMPANY_1;
import static controller.TestHelper.JSON_EXAMPLE_1_IDENTIFICATION_1;
import static controller.TestHelper.JSON_EXAMPLE_1_IDENTIFICATION_2;
import static controller.TestHelper.JSON_IDENTIFICATION_1;
import static controller.TestHelper.JSON_IDENTIFICATION_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;


public class IdentificationControllerTest {

	@Test
	public void testGetIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				JsonNode company = Json.parse(JSON_COMPANY_1);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company).get(10000).getStatus());

				JsonNode identification = Json.parse(JSON_IDENTIFICATION_1);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());

				identification = Json.parse(JSON_IDENTIFICATION_2);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());
				
				JsonNode identificationsJson = WS.url(ENDPOINT_IDENTIFICATION).get().get(10000).asJson();
				//Identification id = (Identification) Json.fromJson(identificationsJson.get(0), Identification.class);
				
				assertEquals(2, identificationsJson.size());

			}
		});

	}

	@Test
	public void testPostIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				
				JsonNode company = Json.parse(JSON_COMPANY_1);
				assertEquals(WS.url(ENDPOINT_ADD_COMPANY).post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse(JSON_IDENTIFICATION_1);
				assertEquals(WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus(), OK);
				
			}
		});

	}
	
	@Test
	public void testPostIdentificationWithInexistentCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				
				JsonNode identification = Json.parse(JSON_IDENTIFICATION_1);
				
				WSResponse response = WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000);
				
				assertEquals(BAD_REQUEST, response.getStatus());
				
				assertTrue("Expected error message not found.", response.asJson().get("message").asText()
						.contains("The associated company was not found"));				
			}
		});

	}
	
	@Test
	public void testExample1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				JsonNode company = Json.parse(JSON_EXAMPLE_1_COMPANY_1);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company).get(10000).getStatus());

				JsonNode identification = Json.parse(JSON_EXAMPLE_1_IDENTIFICATION_1);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());

				identification = Json.parse(JSON_EXAMPLE_1_IDENTIFICATION_2);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());
				
				JsonNode identificationsJson = WS.url(ENDPOINT_IDENTIFICATION).get().get(10000).asJson();
				
				assertEquals(2,identificationsJson.size());
				assertEquals(2,identificationsJson.get(0).get("id").asLong());
				assertEquals(1,identificationsJson.get(1).get("id").asLong());

			}
		});

	}

	@Test
	public void testExample2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				JsonNode company1 = Json.parse(JSON_EXAMPLE_2_COMPANY_1);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company1).get(10000).getStatus());

				JsonNode company2 = Json.parse(JSON_EXAMPLE_2_COMPANY_2);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company2).get(10000).getStatus());
				
				JsonNode identification = Json.parse(JSON_EXAMPLE_2_IDENTIFICATION_1);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());

				identification = Json.parse(JSON_EXAMPLE_2_IDENTIFICATION_2);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());
				
				JsonNode identificationsJson = WS.url(ENDPOINT_IDENTIFICATION).get().get(10000).asJson();
				
				assertEquals(2,identificationsJson.size());
				assertEquals(2,identificationsJson.get(0).get("id").asLong());
				assertEquals(1,identificationsJson.get(1).get("id").asLong());

			}
		});

	}
	
	@Test
	public void testExample3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				JsonNode company1 = Json.parse(JSON_EXAMPLE_3_COMPANY_1);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company1).get(10000).getStatus());

				JsonNode company2 = Json.parse(JSON_EXAMPLE_3_COMPANY_2);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company2).get(10000).getStatus());
				
				JsonNode identification = Json.parse(JSON_EXAMPLE_2_IDENTIFICATION_1);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());

				identification = Json.parse(JSON_EXAMPLE_2_IDENTIFICATION_2);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());
				
				JsonNode identificationsJson = WS.url(ENDPOINT_IDENTIFICATION).get().get(10000).asJson();
				
				assertEquals(2,identificationsJson.size());
				assertEquals(1,identificationsJson.get(0).get("id").asLong());
				assertEquals(2,identificationsJson.get(1).get("id").asLong());

			}
		});

	}
	
	@Test
	public void testExample4() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {

				JsonNode company1 = Json.parse(JSON_EXAMPLE_4_COMPANY_1);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company1).get(10000).getStatus());

				JsonNode company2 = Json.parse(JSON_EXAMPLE_4_COMPANY_2);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company2).get(10000).getStatus());
				
				JsonNode identification = Json.parse(JSON_EXAMPLE_4_IDENTIFICATION_1);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());

				identification = Json.parse(JSON_EXAMPLE_4_IDENTIFICATION_2);
				assertEquals(OK, WS.url(ENDPOINT_START_IDENTIFICATION).post(identification).get(10000).getStatus());
				
				JsonNode identificationsJson = WS.url(ENDPOINT_IDENTIFICATION).get().get(10000).asJson();
				
				assertEquals(2,identificationsJson.size());
				assertEquals(2,identificationsJson.get(0).get("id").asLong());
				assertEquals(1,identificationsJson.get(1).get("id").asLong());

			}
		});

	}

}
