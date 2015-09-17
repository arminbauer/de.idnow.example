import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.After;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

public class PostIdentificationTest extends RestControllerTest {

	@Test
	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
			//identification without company
			JsonNode identification = Json.parse(BASEIDENT);
			assertEquals(WS.url(POSTIDENTURL).post(identification).get(10000).getStatus(), BAD_REQUEST);
	
			//valid identification
			JsonNode company = Json.parse(BASECOMPANY);
			assertEquals(WS.url(POSTCOMPANYURL).post(company).get(10000).getStatus(), OK);								
			assertEquals(WS.url(POSTIDENTURL).post(identification).get(10000).getStatus(), OK);
	
			WSResponse identDataRepsonse = WS.url(GETIDENTURL).get().get(10000);
			assertEquals(identDataRepsonse.getStatus(), OK);
			JsonNode identData = identDataRepsonse.asJson(); 
			assert identData.isArray();
			assertEquals(identData.get(0).get("id").asInt(), 1);
	
	
			//duplicate identification						
			assertEquals(WS.url(POSTIDENTURL).post(identification).get(10000).getStatus(), BAD_REQUEST);
			
			//missing data identification 
			JsonNode missingDataIdentification = Json.parse(MISSINGDATAIDENT);
			assertEquals(WS.url(POSTIDENTURL).post(missingDataIdentification).get(10000).getStatus(), BAD_REQUEST);
	
			//clearing identification data
			assertEquals(WS.url(DELETEIDENTURL).delete().get(10000).getStatus(), OK);	
			identData = WS.url(GETIDENTURL).get().get(10000).asJson();				
			assert identData.isArray();
			assertEquals(identData.toString(), "[]");
			
			 WS.url(DELETECOMPANYURL).delete();
			 
		});
	
	}

}
