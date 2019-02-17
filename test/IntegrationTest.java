import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Integration tests
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IntegrationTest {

    @Test
    public void testIdentificationsOrder() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {

            JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank 1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

            JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.8}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

            JsonNode company3 = Json.parse("{\"id\": 3, \"name\": \"Test Bank 3\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company3).get(10000).getStatus(), OK);

            JsonNode company4 = Json.parse("{\"id\": 4, \"name\": \"Test Bank 4\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.9}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company4).get(10000).getStatus(), OK);

            JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Client 1\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

            JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Client 2\", \"time\": 1435667215, \"waiting_time\": 60, \"companyid\": 2}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

            JsonNode identification3 = Json.parse("{\"id\": 3, \"name\": \"Client 3\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 2}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification3).get(10000).getStatus(), OK);

            JsonNode identification4 = Json.parse("{\"id\": 4, \"name\": \"Client 4\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 3}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification4).get(10000).getStatus(), OK);

            JsonNode identification5 = Json.parse("{\"id\": 5, \"name\": \"Client 5\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 4}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification5).get(10000).getStatus(), OK);

            assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getBody(),
                    "[{\"id\":2,\"name\":\"Client 2\",\"time\":1435667215,\"waiting_time\":60,\"companyid\":2}," +
                            "{\"id\":3,\"name\":\"Client 3\",\"time\":1435667215,\"waiting_time\":45,\"companyid\":2}," +
                            "{\"id\":5,\"name\":\"Client 5\",\"time\":1435667215,\"waiting_time\":45,\"companyid\":4}," +
                            "{\"id\":1,\"name\":\"Client 1\",\"time\":1435667215,\"waiting_time\":45,\"companyid\":1}," +
                            "{\"id\":4,\"name\":\"Client 4\",\"time\":1435667215,\"waiting_time\":30,\"companyid\":3}]");
        });
    }

    @Test
    public void testIdentificationsOrderWithWeights() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {

            JsonNode weightSettings = Json.parse("{\"id\": 1, \"sla_difference_weight\": 0.53, \"sla_weight\": 0.45, \"waiting_time_weight\": 0.02}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addWeightSettings").post(weightSettings).get(10000).getStatus(), OK);

            JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank 1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

            JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 120, \"sla_percentage\": 0.2, \"current_sla_percentage\": 0.2}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

            JsonNode company3 = Json.parse("{\"id\": 3, \"name\": \"Test Bank 3\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company3).get(10000).getStatus(), OK);

            JsonNode company4 = Json.parse("{\"id\": 4, \"name\": \"Test Bank 4\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.9}");
            assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company4).get(10000).getStatus(), OK);

            JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Client 1\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

            JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Client 2\", \"time\": 1435667215, \"waiting_time\": 60, \"companyid\": 2}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

            JsonNode identification3 = Json.parse("{\"id\": 3, \"name\": \"Client 3\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 2}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification3).get(10000).getStatus(), OK);

            JsonNode identification4 = Json.parse("{\"id\": 4, \"name\": \"Client 4\", \"time\": 1435667215, \"waiting_time\": 300, \"companyid\": 3}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification4).get(10000).getStatus(), OK);

            JsonNode identification5 = Json.parse("{\"id\": 5, \"name\": \"Client 5\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 4}");
            assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification5).get(10000).getStatus(), OK);

            assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getBody(),
                    "[{\"id\":4,\"name\":\"Client 4\",\"time\":1435667215,\"waiting_time\":300,\"companyid\":3}," +
                            "{\"id\":5,\"name\":\"Client 5\",\"time\":1435667215,\"waiting_time\":45,\"companyid\":4}," +
                            "{\"id\":2,\"name\":\"Client 2\",\"time\":1435667215,\"waiting_time\":60,\"companyid\":2}," +
                            "{\"id\":3,\"name\":\"Client 3\",\"time\":1435667215,\"waiting_time\":45,\"companyid\":2}," +
                            "{\"id\":1,\"name\":\"Client 1\",\"time\":1435667215,\"waiting_time\":45,\"companyid\":1}]");
        });
    }
}
