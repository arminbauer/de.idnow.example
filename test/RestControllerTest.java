import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Iterables;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import services.dto.CompanyDTO;
import services.dto.IdentificationDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class RestControllerTest {


    private static final String HOST = "http://localhost:3333";

    private static final String ADD_COMPANY = "/api/v1/addCompany";

    private static final String START_IDENTIFICATION = "/api/v1/startIdentification";

    private static final String IDENTIFICATIONS = "/api/v1/identifications";

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

    /**
     * Example 1:
     * <p>
     * One company with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Identification 1: Waiting_time=30
     * Identification 2: Waiting_time=45 Expected order: Identification 2, Identification 1 (since Ident 2 has waited longer)
     */
    @Test
    public void postIdentificationFromExample1() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company1 = createCompany(1, "Company 1", 60, 0.9, 0.95);
            JsonNode ident1 = createIdentification(1, "Identification 1", 1435667215, 30, 1);
            JsonNode ident2 = createIdentification(2, "Identification 2", 1435667215, 45, 1);
            assertEquals(WS.url(HOST + ADD_COMPANY).post(company1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident2).get(1000).getStatus(), OK);
            JsonNode identification = WS.url(HOST + IDENTIFICATIONS).get().get(1000).asJson();
            assertTrue(identification.isArray());

            assertEquals(2, Iterables.get(identification, 0).get("id").asInt());
            assertEquals(1, Iterables.get(identification, 1).get("id").asInt());

        });
    }

    /**
     * Example 2:
     * <p>
     * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Company 2 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.90
     * Identification 1 belonging to Company1: Waiting_time=30
     * Identification 2 belonging to Company2: Waiting_time=30 Expected order: Identification 2, Identification 1
     * (since Company 2 already has a lower current SLA percentage in this month, so its identifications have higher prio)
     */
    @Test
    public void postIdentificationFromExample2() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company1 = createCompany(1, "Company 1", 60, 0.9, 0.95);
            JsonNode company2 = createCompany(2, "Company 2", 60, 0.9, 0.90);

            JsonNode ident1 = createIdentification(1, "Identification 1", 1435667215, 30, 1);
            JsonNode ident2 = createIdentification(2, "Identification 2", 1435667215, 30, 2);
            assertEquals(WS.url(HOST + ADD_COMPANY).post(company1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + ADD_COMPANY).post(company2).get(1000).getStatus(), OK);

            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident2).get(1000).getStatus(), OK);

            JsonNode identification = WS.url(HOST + IDENTIFICATIONS).get().get(1000).asJson();
            assertTrue(identification.isArray());

            assertEquals(2, Iterables.size(identification));
            assertEquals(1, Iterables.get(identification, 1).get("id").asInt());
            assertEquals(2, Iterables.get(identification, 0).get("id").asInt());
        });
    }

    @Test
    public void postIdentificationFromExample3() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {

            JsonNode company1 = createCompany(1, "Company 1", 60, 0.9, 0.95);
            JsonNode company2 = createCompany(2, "Company 2", 120, 0.8, 0.95);

            JsonNode ident1 = createIdentification(1, "Identification 1", 1435667215, 30, 1);
            JsonNode ident2 = createIdentification(2, "Identification 2", 1435667215, 30, 2);
            assertEquals(WS.url(HOST + ADD_COMPANY).post(company1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + ADD_COMPANY).post(company2).get(1000).getStatus(), OK);

            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident2).get(1000).getStatus(), OK);

            JsonNode identification = WS.url(HOST + IDENTIFICATIONS).get().get(1000).asJson();
            assertTrue(identification.isArray());

            assertEquals(Iterables.get(identification, 0).get("id").asInt(), 1);
            assertEquals(Iterables.get(identification, 1).get("id").asInt(), 2);
        });
    }

    @Test
    public void postIdentificationFromExample4() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company1 = createCompany(1, "Company 1", 60, 0.9, 0.95);
            JsonNode company2 = createCompany(2, "Company 2", 120, 0.8, 0.8);

            JsonNode ident1 = createIdentification(1, "Identification 1", 1435667215, 45, 1);
            JsonNode ident2 = createIdentification(2, "Identification 2", 1435667215, 30, 2);

            assertEquals(WS.url(HOST + ADD_COMPANY).post(company1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + ADD_COMPANY).post(company2).get(1000).getStatus(), OK);

            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident2).get(1000).getStatus(), OK);

            JsonNode identification = WS.url(HOST + IDENTIFICATIONS).get().get(1000).asJson();
            assertTrue(identification.isArray());

            assertEquals(Iterables.get(identification, 0).get("id").asInt(), 1);
            assertEquals(Iterables.get(identification, 1).get("id").asInt(), 2);
        });
    }

    @Test
    public void postIdentificationFromExample5() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            JsonNode company1 = createCompany(1, "Company 1", 60, 0.8, 0.95);
            JsonNode company2 = createCompany(2, "Company 2", 60, 0.9, 0.9);

            JsonNode ident1 = createIdentification(1, "Identification 1", 1435667215, 30, 1);
            JsonNode ident2 = createIdentification(2, "Identification 2", 1435667215, 30, 2);

            assertEquals(WS.url(HOST + ADD_COMPANY).post(company1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + ADD_COMPANY).post(company2).get(1000).getStatus(), OK);

            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident1).get(1000).getStatus(), OK);
            assertEquals(WS.url(HOST + START_IDENTIFICATION).post(ident2).get(1000).getStatus(), OK);

            JsonNode identification = WS.url(HOST + IDENTIFICATIONS).get().get(1000).asJson();
            assertTrue(identification.isArray());

            assertEquals(Iterables.get(identification, 0).get("id").asInt(), 2);
            assertEquals(Iterables.get(identification, 1).get("id").asInt(), 1);
        });
    }
}
