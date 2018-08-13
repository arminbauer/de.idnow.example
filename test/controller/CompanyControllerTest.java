package controller;

import static controller.TestHelper.*;

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

public class CompanyControllerTest {


	@Test
	public void testCreateCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_1);
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company).get(10000).getStatus());
			}
		});
	}
	
	@Test
	public void testCreateCompanyWithoutId() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_NO_ID);
				
				WSResponse response = WS.url(ENDPOINT_ADD_COMPANY).post(company).get(10000);
				
				assertEquals(BAD_REQUEST, response.getStatus());
				
				assertTrue("Expected error message not found.", response.asJson().get("message").asText()
						.contains("The company id must be provided."));
			}
		});
	}
	
	@Test
	public void testCreateDuplicatedCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_1);
				
				assertEquals(OK, WS.url(ENDPOINT_ADD_COMPANY).post(company).get(10000).getStatus());
				
				WSResponse response = WS.url(ENDPOINT_ADD_COMPANY).post(company).get(10000);
				
				assertEquals(BAD_REQUEST, response.getStatus());
				
				assertTrue("Expected error message not found.", response.asJson().get("message").asText()
						.contains("There is already a company with this id"));
			}
		});
	}

}
