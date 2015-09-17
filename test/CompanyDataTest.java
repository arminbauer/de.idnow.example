import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
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

public class CompanyDataTest extends RestControllerTest{

	@Test
	public void companyData() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {			
			//adding one company
			JsonNode testBank = Json.parse(BASECOMPANY);
			assertEquals(WS.url(POSTCOMPANYURL).post(testBank).get(10000).getStatus(), OK);
	
			JsonNode companyData = WS.url(GETCOMPANYURL).get().get(10000).asJson();				
			assert(companyData.isArray());
			assertEquals(companyData.get(0).get("id").asInt(), 1);

			//adding second company
			JsonNode testShop = Json.parse(SLATIMEDIF);
			assertEquals(WS.url(POSTCOMPANYURL).post(testShop).get(10000).getStatus(), OK);
	
	
			WSResponse companyDataResponse = WS.url(GETCOMPANYURL).get().get(10000);
			assertEquals(companyDataResponse.getStatus(), OK);
			companyData = companyDataResponse.asJson();
			assert(companyData.isArray());
			//company order not defined
			if (companyData.get(0).get("id").asInt(-1) == 1) {
				assert companyData.get(1).get("id").asInt() == 0; 
			} else {
				assert companyData.get(0).get("id").asInt(-1) == 0;
				assert companyData.get(1).get("id").asInt() == 1; 
	
			}
		
			//entering duplicated company Data
			assertEquals(WS.url(POSTCOMPANYURL).post(testBank).get(10000).getStatus(), BAD_REQUEST);
			
			//entering incomplete companyData
			JsonNode missingDataCompany = Json.parse(MISSINGDATACOMPANY);
			assertEquals(WS.url(POSTCOMPANYURL).post(missingDataCompany).get(10000).getStatus(), BAD_REQUEST);
	
			//clearing company data
			assertEquals(WS.url(DELETECOMPANYURL).delete().get(10000).getStatus(), OK);				
			companyData = WS.url(GETCOMPANYURL).get().get(10000).asJson();				
			assert companyData.isArray();
			assertEquals(companyData.toString(), "[]");	
			 WS.url(DELETECOMPANYURL).delete();		
			 
		});
	}



}
