import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

public class SortIdentifcationTest extends RestControllerTest {

	@Test
	public void sortIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			
			//adding companies
			JsonNode testBank = Json.parse(BASECOMPANY);
			assertEquals(WS.url(POSTCOMPANYURL).post(testBank).get(10000).getStatus(), OK);
			JsonNode testShop = Json.parse(SLATIMEDIF);
			assertEquals(WS.url(POSTCOMPANYURL).post(testShop).get(10000).getStatus(), OK);
			JsonNode testOffice = Json.parse(CURRENTSLAPERCENTDIF);
			assertEquals(WS.url(POSTCOMPANYURL).post(testOffice).get(10000).getStatus(), OK);
			JsonNode testHome = Json.parse(SLAPERCENTDIF);
			assertEquals(WS.url(POSTCOMPANYURL).post(testHome).get(10000).getStatus(), OK);
			JsonNode startUp = Json.parse(EVERYTHINGDIFFERENTCOMPANY);
			assertEquals(WS.url(POSTCOMPANYURL).post(startUp).get(10000).getStatus(), OK);
	
			//different wait times and ids
			JsonNode peterHuber = Json.parse(BASEIDENT);						
			assertEquals(WS.url(POSTIDENTURL).post(peterHuber).get(10000).getStatus(), OK);
			JsonNode jonDoe = Json.parse(DIFFENRTWAITTIMEIDENT);
			assertEquals(WS.url(POSTIDENTURL).post(jonDoe).get(10000).getStatus(), OK);
			JsonNode nedFlanders = Json.parse(EVRYTHINGSAMEIDENT);
			assertEquals(WS.url(POSTIDENTURL).post(nedFlanders).get(10000).getStatus(), OK);
	
			WSResponse identDataRepsonse = WS.url(GETIDENTURL).get().get(10000);
			assertEquals(identDataRepsonse.getStatus(), OK);
			JsonNode identData = identDataRepsonse.asJson(); 
			assert identData.isArray();
			assertEquals(identData.get(0).get("id").asInt(), 2); //jon doe wait time 30
			assertEquals(identData.get(1).get("id").asInt(), 1); //peter huber wait time 10				
			assertEquals(identData.get(2).get("id").asInt(), 6); //ned flanders wait time 10 and a higher Id as peter huber
	
			//reset
			assertEquals(WS.url(DELETEIDENTURL).delete().get(10000).getStatus(), OK);	
	
			//different sla time
			assertEquals(WS.url(POSTIDENTURL).post(peterHuber).get(10000).getStatus(), OK);
			JsonNode liseMueller = Json.parse(LISEMUELLER);
			assertEquals(WS.url(POSTIDENTURL).post(liseMueller).get(10000).getStatus(), OK);
	
			identDataRepsonse = WS.url(GETIDENTURL).get().get(10000);
			assertEquals(identDataRepsonse.getStatus(), OK);
			identData = identDataRepsonse.asJson(); 
			assert identData.isArray();
			assertEquals(identData.get(0).get("id").asInt(), 1); //works for bank SLA time 60
			assertEquals(identData.get(1).get("id").asInt(), 4); //works for shop SLA time 90
	
			//reset
			assertEquals(WS.url(DELETEIDENTURL).delete().get(10000).getStatus(), OK);	
	
			//different current SLA percentage 
			assertEquals(WS.url(POSTIDENTURL).post(peterHuber).get(10000).getStatus(), OK);
			JsonNode maxMustermann = Json.parse(MAXMUSTERMAN);
			assertEquals(WS.url(POSTIDENTURL).post(maxMustermann).get(10000).getStatus(), OK);
	
			identDataRepsonse = WS.url(GETIDENTURL).get().get(10000);
			assertEquals(identDataRepsonse.getStatus(), OK);
			identData = identDataRepsonse.asJson(); 
			assert identData.isArray();
			assertEquals(identData.get(0).get("id").asInt(), 3); //works for office current SLA percentage 92
			assertEquals(identData.get(1).get("id").asInt(), 1); //works for bank current SLA percentage 95
	
			//reset
			assertEquals(WS.url(DELETEIDENTURL).delete().get(10000).getStatus(), OK);	
	
			//different current SLA percentage 
			assertEquals(WS.url(POSTIDENTURL).post(peterHuber).get(10000).getStatus(), OK);
			JsonNode homeWorker = Json.parse(HOMEIDENT);				
			assertEquals(WS.url(POSTIDENTURL).post(homeWorker).get(10000).getStatus(), OK);
	
			identDataRepsonse = WS.url(GETIDENTURL).get().get(10000);
			assertEquals(identDataRepsonse.getStatus(), OK);
			identData = identDataRepsonse.asJson(); 
			assert identData.isArray();
			assertEquals(identData.get(0).get("id").asInt(), 5); //works form home SLA percentage 92
			assertEquals(identData.get(1).get("id").asInt(), 1); //works for bank SLA percentage 90
	
			//reset
			assertEquals(WS.url(DELETEIDENTURL).delete().get(10000).getStatus(), OK);					
	
			//everything different
			assertEquals(WS.url(POSTIDENTURL).post(peterHuber).get(10000).getStatus(), OK);
			JsonNode che = Json.parse(CHE);				
			assertEquals(WS.url(POSTIDENTURL).post(che).get(10000).getStatus(), OK);
	
			identDataRepsonse = WS.url(GETIDENTURL).get().get(10000);
			assertEquals(identDataRepsonse.getStatus(), OK);
			identData = identDataRepsonse.asJson(); 
			assert identData.isArray();
			assertEquals(identData.get(0).get("id").asInt(), 1); 
			assertEquals(identData.get(1).get("id").asInt(), 6);
			
			 WS.url(DELETECOMPANYURL).delete();
			 
		});
	
	}



}
