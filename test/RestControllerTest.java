import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import services.dto.CompanyDTO;
import services.dto.IdentificationDTO;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class RestControllerTest {


    private static final String HOST = "http://localhost:3333/";

    private static final String ADD_COMPANY = "/api/v1/addCompany";

    private static final String START_IDENTIFICATION = "/api/v1/startIdentification";

    JsonNode identifications;

    private static JsonNode createCompany(int id, String name, long slaTime, double slaPercentage, double currentSlaPercentage) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(id);
        companyDTO.setName(name);
        companyDTO.setSlaTime(slaTime);
        companyDTO.setSlaPercentage(slaPercentage);
        companyDTO.setCurrentSlaPercentage(currentSlaPercentage);
        return Json.toJson(companyDTO);
    }

    private static JsonNode createIdentification(int id, String name, long time, long waitingTime, int companyId) {
        IdentificationDTO identificationDTO = new IdentificationDTO();
        identificationDTO.setId(id);
        identificationDTO.setName(name);
        identificationDTO.setTime(time);
        identificationDTO.setWaitingTime(waitingTime);
        identificationDTO.setCompanyId(companyId);
        return Json.toJson(identificationDTO);
    }

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

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyId\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
            }
        });
    }

    @Test
    public void postIdentificationFromExample1() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company1 = createCompany(1, "Company 1", 60, 0.9, 0.95);
            JsonNode ident1 = createIdentification(1, "Identification 1", 1435667215, 30, 1);
            JsonNode ident2 = createIdentification(2, "Identification 2", 1435667215, 45, 1);
            assertEquals(WS.url(HOST + ADD_COMPANY).post(company1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident2).get(1000).getStatus(), OK);



        });
    }

}
