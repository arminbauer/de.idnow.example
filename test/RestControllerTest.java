import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Identification;
import org.junit.Test;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Result;
import play.test.Helpers;

import java.io.IOException;
import java.util.List;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class RestControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void get_identifications_of_same_company_with_different_waiting_times() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {

            @Override
            public void run() {
                postSampleCompany(1l, 60l, 0.9f, 0.95f);
                postSampleIdentification(1l, 30l);
                postSampleIdentification(1l, 45l);
                Result resultGetIdentifications = route(Helpers.fakeRequest("GET", "http://localhost:3333/api/v1/identifications"));
                try {
                    List<Identification> identifications = objectMapper.readValue(contentAsString(resultGetIdentifications), new TypeReference<List<Identification>>() {
                    });

                    assertTrue(identifications.size() == 2);
                    assertTrue(identifications.get(0).getId() == 2);
                    assertTrue(identifications.get(1).getId() == 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Test
    public void get_identifications_of_two_different_companies_with_same_sla_percentage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {

            @Override
            public void run() {
                postSampleCompany(1l, 60l, 0.9f, 0.95f);
                postSampleCompany(2l, 60l, 0.9f, 0.90f);
                postSampleIdentification(1l, 30l);
                postSampleIdentification(2l, 30l);
                Result resultGetIdentifications = route(Helpers.fakeRequest("GET", "http://localhost:3333/api/v1/identifications"));
                try {
                    List<Identification> identifications = objectMapper.readValue(contentAsString(resultGetIdentifications), new TypeReference<List<Identification>>() {
                    });

                    assertTrue(identifications.size() == 2);
                    assertTrue(identifications.get(0).getId() == 2);
                    assertTrue(identifications.get(1).getId() == 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Test
    public void get_identifications_of_two_different_companies_with_same_current_sla_percentage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {

            @Override
            public void run() {
                postSampleCompany(1l, 60l, 0.9f, 0.95f);
                postSampleCompany(2l, 120l, 0.8f, 0.95f);
                postSampleIdentification(1l, 30l);
                postSampleIdentification(2l, 30l);
                Result resultGetIdentifications = route(Helpers.fakeRequest("GET", "http://localhost:3333/api/v1/identifications"));

                try {
                    List<Identification> identifications = objectMapper.readValue(contentAsString(resultGetIdentifications), new TypeReference<List<Identification>>() {
                    });
                    assertTrue(identifications.size() == 2);

                    assertTrue(identifications.get(0).getId() == 1);
                    assertTrue(identifications.get(1).getId() == 2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Test
    public void get_identifications_of_two_different_companies_with_all_different_values() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {

            @Override
            public void run() {
                postSampleCompany(1l, 60l, 0.9f, 0.95f);
                postSampleCompany(2l, 120l, 0.8f, 0.8f);
                postSampleIdentification(1l, 45l);
                postSampleIdentification(2l, 30l);
                Result resultGetIdentifications = route(Helpers.fakeRequest("GET", "http://localhost:3333/api/v1/identifications"));
                try {
                    List<Identification> identifications = objectMapper.readValue(contentAsString(resultGetIdentifications), new TypeReference<List<Identification>>() {
                    });

                    assertTrue(identifications.size() == 2);
                    assertTrue(identifications.get(0).getId() == 2);
                    assertTrue(identifications.get(1).getId() == 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Test
    public void get_identifications_of_two_different_companies_with_same_sla_difference() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {

            @Override
            public void run() {
                postSampleCompany(1l, 60l, 0.9f, 0.95f);
                postSampleCompany(2l, 120l, 0.8f, 0.75f);
                postSampleIdentification(1l, 30l);
                postSampleIdentification(2l, 45l);
                Result resultGetIdentifications = route(Helpers.fakeRequest("GET", "http://localhost:3333/api/v1/identifications"));
                try {
                    List<Identification> identifications = objectMapper.readValue(contentAsString(resultGetIdentifications), new TypeReference<List<Identification>>() {
                    });

                    assertTrue(identifications.size() == 2);
                    assertTrue(identifications.get(0).getId() == 2);
                    assertTrue(identifications.get(1).getId() == 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Test
    public void postIdentification() throws IOException {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                Long companyId = 0l;
                //post a sample company
                JsonNode companyJson = Json.parse("{\"companyName\": \"Test Bank\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSlaPercentage\": 0.95}");
                Result resultCompany = route(Helpers.fakeRequest("POST", "http://localhost:3333/api/v1/addCompany").bodyJson(companyJson));
                assertEquals(resultCompany.status(), OK);
                try {
                    companyId = objectMapper.readValue(contentAsString(resultCompany), Long.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //post a sample identification.
                JsonNode identification = Json.parse("{\"name\": \"Peter Huber\", \"startTimestamp\": 1435667215, \"waitingTime\": 10}");
                Result resultIdentification = route(Helpers.fakeRequest("POST", "http://localhost:3333/api/v1/startIdentification/" + companyId).bodyJson(identification));
                assertEquals(resultIdentification.status(), OK);

            }
        });
    }

    private void postSampleIdentification(Long companyId, Long waitingTime) {
        JsonNode identificationOne = Json.parse("{\"name\": \"Peter Huber\", \"startTimestamp\": 1435667215, \"waitingTime\":" + waitingTime + "}");
        Result resultIdentificationOne = route(Helpers.fakeRequest("POST", "http://localhost:3333/api/v1/startIdentification/" + companyId).bodyJson(identificationOne));
        assertEquals(resultIdentificationOne.status(), OK);
    }

    private void postSampleCompany(Long companyId, Long slaTime, Float slaPercentage, Float currentSlaPercentage) {
        JsonNode companyJson = Json.parse("{\"id\":" + companyId + ", \"companyName\": \"Test Bank\", \"slaTime\":" + slaTime + ", \"slaPercentage\":" + slaPercentage + ", \"currentSlaPercentage\":" + currentSlaPercentage + "}");
        Result resultCompany = route(Helpers.fakeRequest("POST", "http://localhost:3333/api/v1/addCompany").bodyJson(companyJson));
        assertEquals(resultCompany.status(), OK);
    }


}
