
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;


import database.Repository;
import org.junit.Test;
import org.junit.Before;
import play.libs.Json;
import play.libs.ws.WS;

import com.fasterxml.jackson.databind.JsonNode;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
class RestControllerTest {

	JsonNode identifications;

	private static final String ADD_COMPANY_URL = "http://localhost:3333/api/v1/addCompany";
	private static final String START_IDENTIFICATION_URL = "http://localhost:3333/api/v1/startIdentification";
	private static final String GET_IDENTIFICATIONS_URL = "http://localhost:3333/api/v1/identifications";

	//Valid Company data
	private static final String JSON_COMPANY_VALID_DATA_1 ="{\"Id\": 1, \"Name\": \"Company1\", \"SLA_time\": 60,\"SLA_percentage\": 0.8,\"Current_SLA_percentage\": 0.85}";
	private static final String JSON_COMPANY_VALID_DATA_2 ="{\"Id\": 2, \"Name\": \"Company2\", \"SLA_time\": 60,\"SLA_percentage\": 0.9,\"Current_SLA_percentage\": 0.85}";
	private static final String JSON_COMPANY_VALID_DATA_3 ="{\"Id\": 3, \"Name\": \"Company3\", \"SLA_time\": 50,\"SLA_percentage\": 0.9,\"Current_SLA_percentage\": 0.85}";
	private static final String JSON_COMPANY_VALID_DATA_4 ="{\"Id\": 4, \"Name\": \"Company4\", \"SLA_time\": 50,\"SLA_percentage\": 0.8,\"Current_SLA_percentage\": 0.85}";

	//Invalid Company data
	private static final String JSON_COMPANY_INVALID_DATA_1 ="{ \"Name\": \"Company1\", \"SLA_time\": 60,\"SLA_percentage\": 0.8,\"Current_SLA_percentage\": 0.85}";
	private static final String JSON_COMPANY_INVALID_DATA_2 ="{\"Id\": 2, \"SLA_time\": 60,\"SLA_percentage\": 0.9,\"Current_SLA_percentage\": 0.85}";
	private static final String JSON_COMPANY_INVALID_DATA_3 ="{\"Id\": 3, \"Name\": \"Company3\", \"SLA_percentage\": 0.9,\"Current_SLA_percentage\": 0.85}";
	private static final String JSON_COMPANY_INVALID_DATA_4 ="{\"Id\": 4, \"Name\": \"Company4\", \"SLA_time\": 50}";

	//Valid identifications
	private static final String JSON_IDENTIFICATION_1 = "{\"Id\":100,\"Name\":\"id100\",\"Time\": 12345670,\"Waiting_time\": 50,\"Companyid\":1}";
	private static final String JSON_IDENTIFICATION_2 = "{\"Id\":102,\"Name\":\"id102\",\"Time\": 12345670,\"Waiting_time\": 60,\"Companyid\":1}";
	private static final String JSON_IDENTIFICATION_3 = "{\"Id\":103,\"Name\":\"id103\",\"Time\": 2345670,\"Waiting_time\": 40,\"Companyid\":1}";
	private static final String JSON_IDENTIFICATION_4 = "{\"Id\":104,\"Name\":\"id104\",\"Time\": 2345670,\"Waiting_time\": 50,\"Companyid\":1}";
	private static final String JSON_IDENTIFICATION_5 = "{\"Id\":105,\"Name\":\"id105\",\"Time\": 12345670,\"Waiting_time\": 90,\"Companyid\":1}";
	private static final String JSON_IDENTIFICATION_6 = "{\"Id\":106,\"Name\":\"id106\",\"Time\": 12345670,\"Waiting_time\": 140,\"Companyid\":1}";
	private static final String JSON_IDENTIFICATION_7 = "{\"Id\":100,\"Name\":\"id100\",\"Time\": 12345670,\"Waiting_time\": 50,\"Companyid\":2}";
	private static final String JSON_IDENTIFICATION_8 = "{\"Id\":102,\"Name\":\"id102\",\"Time\": 12345670,\"Waiting_time\": 60,\"Companyid\":2}";
	private static final String JSON_IDENTIFICATION_9 = "{\"Id\":103,\"Name\":\"id103\",\"Time\": 2345670,\"Waiting_time\": 40,\"Companyid\":2}";
	private static final String JSON_IDENTIFICATION_10 = "{\"Id\":104,\"Name\":\"id104\",\"Time\": 2345670,\"Waiting_time\": 50,\"Companyid\":2}";
	private static final String JSON_IDENTIFICATION_11 = "{\"Id\":105,\"Name\":\"id105\",\"Time\": 12345670,\"Waiting_time\": 90,\"Companyid\":2}";
	private static final String JSON_IDENTIFICATION_12 = "{\"Id\":106,\"Name\":\"id106\",\"Time\": 12345670,\"Waiting_time\": 10,\"Companyid\":2}";

	//Invalid identifications data
	private static final String JSON_INVALID_IDENTIFICATION_1 = "{\"Name\":\"id100\",\"Time\": 12345670,\"Waiting_time\": 50,\"Companyid\":1}";
	private static final String JSON_INVALID_IDENTIFICATION_2 = "{\"Id\":102,\"Name\":\"id102\",\"Time\": 12345670}";

	@Before
	public void reset(){
		new Repository().reset();
	}


	@Test
	public void createCompany_valid_test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_VALID_DATA_1);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company).get(10000).getStatus(), OK);
			}
		});
	}

	@Test
	public void createCompany_invalidData_test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_INVALID_DATA_1);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}


	@Test
	public void postIdentification_valid_test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_VALID_DATA_1);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company).get(10000).getStatus(), OK);
				
				JsonNode identification = Json.parse(JSON_IDENTIFICATION_1);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification).get(10000).getStatus(), OK);
			}
		});

	}

	@Test
	public void postIdentification_invalidData_test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_VALID_DATA_1);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse(JSON_INVALID_IDENTIFICATION_1);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification).get(10000).getStatus(), BAD_REQUEST);
			}
		});

	}

	@Test
	public void getIdentifications_test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_VALID_DATA_1);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse(JSON_IDENTIFICATION_1);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification).get(10000).getStatus(), OK);
				assertEquals(WS.url(GET_IDENTIFICATIONS_URL).get().get(10000).getStatus(), OK);
			}
		});
	}

	/***
	 * Comp1: sla_time = 60
	 * identification1 wait_time = 50, id2 wait_time = 60,id3 wait_time = 40
	 * expeceted order is id2,id1,id3
	 ***/
	@Test
	public void getIdentifications_test2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_VALID_DATA_1);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company).get(10000).getStatus(), OK);

				JsonNode identification1 = Json.parse(JSON_IDENTIFICATION_1);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification1).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse(JSON_IDENTIFICATION_2);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification2).get(10000).getStatus(), OK);

				JsonNode identification3 = Json.parse(JSON_IDENTIFICATION_3);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification3).get(10000).getStatus(), OK);


				JsonNode arr = WS.url(GET_IDENTIFICATIONS_URL).get().get(10000).asJson();

				assertEquals(arr.get(0), identification2);
				assertEquals(arr.get(1), identification1);
				assertEquals(arr.get(2), identification3);
			}
		});

	}

	/***
	 * "Company1", "SLA_time": 60,"SLA_percentage": 0.8,"Current_SLA_percentage": 0.85
	 * "Company2", "SLA_time": 60,"SLA_percentage": 0.9,"Current_SLA_percentage": 0.85
	 * id1-> "Id":100,"Name":"id100","Time": 12345670,"Waiting_time": 50,"Companyid":1
	 * id2-> Id":102,"Name":"id102","Time": 12345670,"Waiting_time": 60,"Companyid":1
	 * id3-> Id":102,"Name":"id102","Time": 12345670,"Waiting_time": 60,"Companyid":2
	 * expeceted order is id3,id2,id1
	 ***/

	@Test
	public void getIdentifications_two_comp_test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse(JSON_COMPANY_VALID_DATA_1);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company).get(10000).getStatus(), OK);

				JsonNode identification1 = Json.parse(JSON_IDENTIFICATION_1);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification1).get(10000).getStatus(), OK);

				JsonNode identification2 = Json.parse(JSON_IDENTIFICATION_2);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification2).get(10000).getStatus(), OK);


				JsonNode company2 = Json.parse(JSON_COMPANY_VALID_DATA_2);
				assertEquals(WS.url(ADD_COMPANY_URL).post(company2).get(10000).getStatus(), OK);

				JsonNode identification3 = Json.parse(JSON_IDENTIFICATION_8);
				assertEquals(WS.url(START_IDENTIFICATION_URL).post(identification3).get(10000).getStatus(), OK);

				JsonNode arr = WS.url(GET_IDENTIFICATIONS_URL).get().get(10000).asJson();

				assertEquals(arr.get(0), identification3);
				assertEquals(arr.get(1), identification2);
				assertEquals(arr.get(2), identification1);
			}
		});

	}



}
