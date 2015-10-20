package models;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerTest {
	
	public static final int TIMEOUT 			 = 10000;
	
	public static final String ERROR_FORMAT 	 = "Response-Format is not a JSON-Array";
	public static final String ERROR_CODE_200    = "Response-Code != 200";
	public static final String ERROR_CODE_400    = "Response-Code != 400";
	public static final String ERROR_ORDER		 = "Order invalid";
	
	public static final String COMPANY_A		 = "{\"id\": 1, \"name\": \"08/15 Bank\", \"sla_time\": 50, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.85}";
	public static final String COMPANY_B		 = "{\"id\": 2, \"name\": \"Siberian Banka\", \"sla_time\": 120, \"sla_percentage\": 0.2, \"current_sla_percentage\": 0.2}";
	public static final String COMPANY_C		 = "{\"id\": 3, \"name\": \"Fast Bank\", \"sla_time\": 30, \"sla_percentage\": 0.5, \"current_sla_percentage\": 0.55}";
	public static final String COMPANY_D		 = "{\"id\": 4, \"name\": \"Huber-Bank\", \"sla_time\": 50, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.6}";;
	
	public static final String IDENTIFICATION_A1_1 = "{\"id\": 1, \"name\": \"Braunbär 357\", \"time\": 1, \"waiting_time\": 40, \"companyid\": 1}";
	public static final String IDENTIFICATION_B1_2 = "{\"id\": 2, \"name\": \"Vladimir Putin\", \"time\": 1, \"waiting_time\": 240, \"companyid\": 2}";
	public static final String IDENTIFICATION_C1_3 = "{\"id\": 3, \"name\": \"Bot 1\", \"time\": 1, \"waiting_time\": 15, \"companyid\": 3}";
	public static final String IDENTIFICATION_C2_4 = "{\"id\": 4, \"name\": \"Bot 2\", \"time\": 1, \"waiting_time\": 25, \"companyid\": 3}";
	public static final String IDENTIFICATION_D1_5 = "{\"id\": 5, \"name\": \"Peter Huber\", \"time\": 1, \"waiting_time\": 40, \"companyid\": 4}";
	public static final String IDENTIFICATION_D2_6 = "{\"id\": 6, \"name\": \"Bernd Huber\", \"time\": 1, \"waiting_time\": 30, \"companyid\": 4}";
	public static final String IDENTIFICATION_D3_7 = "{\"id\": 7, \"name\": \"Markus Huber\", \"time\": 1, \"waiting_time\": 50, \"companyid\": 4}";
	

			
	
	// company B's dSLA% is smaller than company A's => 2, 1
	public static final int[] EXPECTED_ORDER_1 = {2, 1};

	// company C's dSLA% is (almost) equal to company A's => sorting based on waiting time => 2, 4, 1, 3
	public static final int[] EXPECTED_ORDER_2 = {2, 4, 1, 3};
	
	// company D's dSLA% is smaller than the other companies' => highest priority + sorting based on waiting time within the 5, 6 and 7 => 7, 5, 6, 2, 4, 1, 3
	public static final int[] EXPECTED_ORDER_3 = {7, 5, 6, 2, 4, 1, 3};
	
	// also see RestController.sortQueue() for a comprehensive overview
	
	/*
		TEST-Setup:
		
		a) checking validation of count for addCompany
		b) checking validation of keys for addCompany
		c) checking adding of identification w/o company
		d) checking adding of identification w/  company
		e) checking order
	*/
	
	@Test
	public void a_validation_count() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				//missing last key-value-pair
				addCompany("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9}", false);
				//												      "current_sla_percentage" is missing ^
			}
		});
	}
	
	@Test
	public void b_validation_keys() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				//typo in first key-value-pair
				addCompany("{\"idx\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}", false);
				//				 ^
			}
		});
	}
	
	@Test
	public void c_no_associated_company() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				//trying to start an identification although no company has been added yet
				startIdentification(IDENTIFICATION_A1_1, false);
			}
		});
	}
	
	@Test
	public void d_with_a_company() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				//adding company first, this time
				addCompany(COMPANY_A, true);
				
				//adding identification second
				startIdentification(IDENTIFICATION_A1_1, true);
			}
		});
	}
	
	@Test
	public void e_order() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				addCompany(COMPANY_A, true);
				startIdentification(IDENTIFICATION_A1_1, true);
				
				
				addCompany(COMPANY_B, true);
				startIdentification(IDENTIFICATION_B1_2, true);
				identifications(EXPECTED_ORDER_1);				//checking
				
				
				addCompany(COMPANY_C, true);
				startIdentification(IDENTIFICATION_C1_3, true);
				startIdentification(IDENTIFICATION_C2_4, true);
				identifications(EXPECTED_ORDER_2);				//checking
				
				
				addCompany(COMPANY_D, true);
				startIdentification(IDENTIFICATION_D1_5, true);
				startIdentification(IDENTIFICATION_D2_6, true);
				startIdentification(IDENTIFICATION_D3_7, true);
				identifications(EXPECTED_ORDER_3);				//checking
			}
		});
	}
	
	
	// -- UTILITIES -------------------------------------------------
	
	
	private WSResponse addCompany(String jsonString, boolean exp200){
		WSResponse response = WS.url("http://localhost:3333/api/v1/addCompany").post(Json.parse(jsonString)).get(TIMEOUT);
		
		// checking for expected status code
		if(exp200)
			assertEquals(ERROR_CODE_200, OK, response.getStatus());
		else
			assertEquals(ERROR_CODE_400, BAD_REQUEST, response.getStatus());
		
		return response;
	}
	
	private WSResponse startIdentification(String jsonString, boolean exp200){
		WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.parse(jsonString)).get(TIMEOUT);
		
		// checking for expected status code
		if(exp200)
			assertEquals(ERROR_CODE_200, OK, response.getStatus());
		else
			assertEquals(ERROR_CODE_400, BAD_REQUEST, response.getStatus());
		
		return response;
	}
	
	private int[] identifications(int[] correctOrder){
		WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(TIMEOUT);
		assertEquals(ERROR_CODE_200, OK, response.getStatus()); 	// checking for expected status code
		
		JsonNode json = Json.parse(response.getBody());
		assertTrue(ERROR_FORMAT, json.isArray());					// checking if root element is an array
		
		int[] ids = getIdsFromJson(json);
		assertArrayEquals(ERROR_ORDER, correctOrder, ids);			// checking if the order is as expected
		
		return ids;
	}
	
	
	private int[] getIdsFromJson(JsonNode idents) {
		ArrayNode arrayNode = (ArrayNode) idents;
		int ids[] = new int[arrayNode.size()];
		
		// putting ids in an array
		for(int i = 0, len = arrayNode.size(); i < len; i++)
			ids[i] = arrayNode.get(i).get("id").asInt();
		
		return ids;
	}
	

}
