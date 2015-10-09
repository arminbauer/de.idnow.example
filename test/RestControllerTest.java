import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.CompanyDTO;
import models.IdentificationDTO;

import org.junit.Test;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

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
				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(response.getStatus(), OK);
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
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
				
				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(response.getStatus(), OK);
				System.out.println(response.getBody());
			}
		});

	}
	
	@Test
	public void identificationOrderTest1() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				
				CompanyDTO company=new CompanyDTO(1L,"company1",60,0.9,0.95);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company)).get(10000).getStatus(), OK);
				
				IdentificationDTO identification1=new IdentificationDTO(1L,"ident1",new Date(),30,1L);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification1)).get(10000).getStatus(), OK);
				
				IdentificationDTO identification2=new IdentificationDTO(2L,"ident2",new Date(),45,1L);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification2)).get(10000).getStatus(), OK);
				
				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(response.getStatus(), OK);
				System.out.println(response.getBody());
				List<IdentificationDTO> list= getIdentList(response);
				assertEquals(identification2,list.get(0));
				assertEquals(identification1,list.get(1));
			}
		});
	}
	
	
	@Test
	public void identificationOrderTest2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				
				CompanyDTO company=new CompanyDTO(1L,"company1",60,0.9,0.95);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company)).get(10000).getStatus(), OK);
				
				CompanyDTO company2=new CompanyDTO(2L,"company2",60,0.9,0.9);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company2)).get(10000).getStatus(), OK);
				
				IdentificationDTO identification1=new IdentificationDTO(1L,"ident1",new Date(),30,1L);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification1)).get(10000).getStatus(), OK);
				
				IdentificationDTO identification2=new IdentificationDTO(2L,"ident2",new Date(),30,2L);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification2)).get(10000).getStatus(), OK);
				
				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(response.getStatus(), OK);
				System.out.println(response.getBody());
				List<IdentificationDTO> list= getIdentList(response);
				assertEquals(identification1,list.get(0));
				assertEquals(identification2,list.get(1));
			}
		});
	}
	
	@Test
	public void identificationOrderTest3() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				
				CompanyDTO company=new CompanyDTO(1L,"company1",60,0.9,0.95);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company)).get(10000).getStatus(), OK);
				
				CompanyDTO company2=new CompanyDTO(2L,"company2",120,0.8,0.8);
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company2)).get(10000).getStatus(), OK);
				
				IdentificationDTO identification1=new IdentificationDTO(1L,"ident1",new Date(),45,1L);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification1)).get(10000).getStatus(), OK);
				
				IdentificationDTO identification2=new IdentificationDTO(2L,"ident2",new Date(),30,2L);
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification2)).get(10000).getStatus(), OK);
				
				WSResponse response = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
				assertEquals(response.getStatus(), OK);
				System.out.println(response.getBody());
				List<IdentificationDTO> list= getIdentList(response);
				assertEquals(identification1,list.get(0));
				assertEquals(identification2,list.get(1));
			}
		});
	}
	
	private static List<IdentificationDTO> getIdentList(WSResponse response){
		List<IdentificationDTO> list=null;
		try {
			list = Json.mapper().readValue(response.getBody(),
					TypeFactory.defaultInstance().constructCollectionType(List.class,  
							IdentificationDTO.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
