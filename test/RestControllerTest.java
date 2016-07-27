import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import play.libs.Json;
import play.libs.ws.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.org.apache.xalan.internal.xsltc.dom.ArrayNodeListIterator;

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
				//assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
			}
		});

	}
	
	/*
	@Test
	public void verifyIdentification1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"One\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Two\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				JsonNode company3 = Json.parse("{\"id\": 3, \"name\": \"Three\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				JsonNode company4 = Json.parse("{\"id\": 4, \"name\": \"Four\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company3).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company4).get(10000).getStatus(), OK);
				
				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Id One\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Id Two\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);
				//ArrayNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).thenApply(WSResponse::asJson);
				JsonNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
				ArrayNode arrayNodeJson = (ArrayNode)identifications;
				assertEquals("The number of identifications is not right!",2,arrayNodeJson.size());
				JsonNode first = arrayNodeJson.get(0);
				JsonNode second = arrayNodeJson.get(1);
				assertEquals("identifications are not in correct order",2,first.findValue("id").asInt());
				assertEquals("identifications are not in correct order",1,second.findValue("id").asInt());
				
	
			}
		});

	}
	*/
	/*
	
	@Test
	public void verifyIdentification2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"One\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Two\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				JsonNode company3 = Json.parse("{\"id\": 3, \"name\": \"Three\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				JsonNode company4 = Json.parse("{\"id\": 4, \"name\": \"Four\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company3).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company4).get(10000).getStatus(), OK);
				
				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Id One\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Id Two\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);
				//ArrayNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).thenApply(WSResponse::asJson);
				JsonNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
				ArrayNode arrayNodeJson = (ArrayNode)identifications;
				assertEquals("The number of identifications is not right!",2,arrayNodeJson.size());
				JsonNode first = arrayNodeJson.get(0);
				JsonNode second = arrayNodeJson.get(1);
				assertEquals("identifications are not in correct order",2,first.findValue("id").asInt());
				assertEquals("identifications are not in correct order",1,second.findValue("id").asInt());
	
			}
		});

	}*/
	
   /*
	@Test
	public void verifyIdentification3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"One\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Two\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				JsonNode company3 = Json.parse("{\"id\": 3, \"name\": \"Three\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				JsonNode company4 = Json.parse("{\"id\": 4, \"name\": \"Four\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company3).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company4).get(10000).getStatus(), OK);
				
				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Id One\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Id Two\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 3}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);
				//ArrayNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).thenApply(WSResponse::asJson);
				JsonNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
				ArrayNode arrayNodeJson = (ArrayNode)identifications;
				assertEquals("The number of identifications is not right!",2,arrayNodeJson.size());
				JsonNode first = arrayNodeJson.get(0);
				JsonNode second = arrayNodeJson.get(1);
				assertEquals("identifications are not in correct order",1,first.findValue("id").asInt());
				assertEquals("identifications are not in correct order",2,second.findValue("id").asInt());
	
			}
		});

	}*/
	/*
	@Test
	public void verifyIdentification4() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"One\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Two\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				JsonNode company3 = Json.parse("{\"id\": 3, \"name\": \"Three\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				JsonNode company4 = Json.parse("{\"id\": 4, \"name\": \"Four\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company3).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company4).get(10000).getStatus(), OK);
				
				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Id One\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Id Two\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 4}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);
				//ArrayNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).thenApply(WSResponse::asJson);
				JsonNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
				ArrayNode arrayNodeJson = (ArrayNode)identifications;
				assertEquals("The number of identifications is not right!",2,arrayNodeJson.size());
				JsonNode first = arrayNodeJson.get(0);
				JsonNode second = arrayNodeJson.get(1);
				assertEquals("identifications are not in correct order",2,first.findValue("id").asInt());
				assertEquals("identifications are not in correct order",1,second.findValue("id").asInt());
	
			}
		});

	}*/
	
	@Test
	public void verifyIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"One\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
				JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Two\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
				JsonNode company3 = Json.parse("{\"id\": 3, \"name\": \"Three\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
				JsonNode company4 = Json.parse("{\"id\": 4, \"name\": \"Four\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
				
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company3).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company4).get(10000).getStatus(), OK);
				
				JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Id One\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
				JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Id Two\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
				JsonNode identification3 = Json.parse("{\"id\": 3, \"name\": \"Id Three\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 3}");
				JsonNode identification4 = Json.parse("{\"id\": 4, \"name\": \"Id Four\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 4}");
				JsonNode identification5 = Json.parse("{\"id\": 5, \"name\": \"Id Five\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
				
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification3).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification4).get(10000).getStatus(), OK);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification5).get(10000).getStatus(), OK);
				//ArrayNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).thenApply(WSResponse::asJson);
				JsonNode identifications = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
				ArrayNode arrayNodeJson = (ArrayNode)identifications;
				assertEquals("The number of identifications is not right!",5,arrayNodeJson.size());
				JsonNode first = arrayNodeJson.get(0);
				JsonNode second = arrayNodeJson.get(1);
				JsonNode third = arrayNodeJson.get(2);
				JsonNode fourth = arrayNodeJson.get(3);
				JsonNode fifth = arrayNodeJson.get(4);
				
				assertEquals("identifications are not in correct order",4,first.findValue("id").asInt());
				assertEquals("identifications are not in correct order",2,second.findValue("id").asInt());
				assertEquals("identifications are not in correct order",5,third.findValue("id").asInt());
				assertEquals("identifications are not in correct order",1,fourth.findValue("id").asInt());
				assertEquals("identifications are not in correct order",3,fifth.findValue("id").asInt());
	
			}
		});

	}

}
