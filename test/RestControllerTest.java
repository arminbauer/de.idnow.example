import com.fasterxml.jackson.databind.JsonNode;
import models.Company;
import models.Identification;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class RestControllerTest {

    JsonNode identifications;

    @Test
    public void getIdentifications() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus());
            }
        });

    }

    @Test
    public void postIdentification() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company = Json.parse("{\"id\": \"123e4567-e89b-42d3-a456-556642440000\", \"name\": \"Test Bank\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSLAPercentage\": 0.95}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus());

                JsonNode identification = Json.parse("{\"id\": \"123e4568-e89b-42d3-a456-556642440000\", \"name\": \"Peter Huber\", \"time\": 1435667215, \"waitingTime\": 10, \"companyId\": \"123e4567-e89b-42d3-a456-556642440000\"}");
                assertEquals(OK, WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus());
            }
        });
    }

    // This replicates example 1.
    @Test
    public void postTwoIdentsOneCompany() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            UUID companyId = UUID.randomUUID();

//            JsonNode company = Json.parse("{\"id\": \"" + companyId.toString() + "\", \"name\": \"Company1\", \"SLATime\": 60, \"SLAPercentage\": 0.9, \"currentSLAPercentage\": 0.95}");
            Company company = new Company(companyId, "Company 1", 60L, 0.9f, 0.95f);
            WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company)).get(10000);

            Identification ident1 = new Identification(UUID.randomUUID(), "Name Unknown 1", 123L, 30L, companyId);
            WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(ident1)).get(10000);

            Identification ident2 = new Identification(UUID.randomUUID(), "Name Unknown 2", 123L, 45L, companyId);
            WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(ident2)).get(10000);

            JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
            assertEquals(45, jsonNode.get(0).get("waitingTime").asInt());
            assertEquals(30, jsonNode.get(1).get("waitingTime").asInt());
        });
    }

    // This replicates example 2.
    @Test
    public void postTwoIdentsTwoCompanies_DifferentCurrentSLA() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            UUID companyId1 = UUID.randomUUID();
            UUID companyId2 = UUID.randomUUID();

            Company company1 = new Company(companyId1, "Company 1", 60L, 0.9f, 0.95f);
            WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company1)).get(10000);

            Company company2 = new Company(companyId2, "Company 2", 60L, 0.9f, 0.9f);
            WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company2)).get(10000);

            UUID id1 = UUID.randomUUID();
            Identification ident1 = new Identification(id1, "Name Unknown 1", 123L, 30L, companyId1);
            WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(ident1)).get(10000);

            UUID id2 = UUID.randomUUID();
            Identification ident2 = new Identification(id2, "Name Unknown 2", 123L, 30L, companyId2);
            WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(ident2)).get(10000);

            JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
            assertEquals(id2.toString(), jsonNode.get(0).get("id").asText());
            assertEquals(id1.toString(), jsonNode.get(1).get("id").asText());

        });
    }

    // This replicates example 3.
    @Test
    public void postTwoIdentsTwoCompanies_DifferentSLA() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            UUID companyId1 = UUID.randomUUID();
            UUID companyId2 = UUID.randomUUID();

            Company company1 = new Company(companyId1, "Company 1", 60L, 0.9f, 0.95f);
            WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company1)).get(10000);

            Company company2 = new Company(companyId2, "Company 2", 120L, 0.8f, 0.95f);
            WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company2)).get(10000);

            UUID id1 = UUID.randomUUID();
            Identification ident1 = new Identification(id1, "Name Unknown 1", 123L, 30L, companyId1);
            WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(ident1)).get(10000);

            UUID id2 = UUID.randomUUID();
            Identification ident2 = new Identification(id2, "Name Unknown 2", 123L, 30L, companyId2);
            WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(ident2)).get(10000);

            JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
            assertEquals(id1.toString(), jsonNode.get(0).get("id").asText());
            assertEquals(id2.toString(), jsonNode.get(1).get("id").asText());

        });
    }

    // This replicates example 4.
    // I assume ident2 has a higher priority here, since company2 has a smaller SLA percentage delta.
    @Test
    public void postTwoIdentsTwoCompanies_DifferentSLA_DifferentPercentages() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            UUID companyId1 = UUID.randomUUID();
            UUID companyId2 = UUID.randomUUID();

            Company company1 = new Company(companyId1, "Company 1", 60L, 0.9f, 0.95f);
            WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company1)).get(10000);

            Company company2 = new Company(companyId2, "Company 2", 120L, 0.8f, 0.8f);
            WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company2)).get(10000);

            UUID id1 = UUID.randomUUID();
            Identification ident1 = new Identification(id1, "Name Unknown 1", 123L, 45L, companyId1);
            WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(ident1)).get(10000);

            UUID id2 = UUID.randomUUID();
            Identification ident2 = new Identification(id2, "Name Unknown 2", 123L, 30L, companyId2);
            WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(ident2)).get(10000);

            JsonNode jsonNode = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).asJson();
            assertEquals(id2.toString(), jsonNode.get(0).get("id").asText());
            assertEquals(id1.toString(), jsonNode.get(1).get("id").asText());

        });
    }
}
