import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import models.Identification;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * @author Manuel Poppe.
 */
public class RestControllerTestExamples extends RestControllerTest {

    @Test
    public void postIdentificationAndGetExample1() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                Logger.debug("postIdentificationAndGetExample1");
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addIdentification").post(identification).get(10000).getStatus(), OK);

                JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Hans Maier\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addIdentification").post(identification2).get(10000).getStatus(), OK);

                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
                assertEquals(wsResponse.getStatus(), OK);
                List<Identification> list = getIdentList(wsResponse);
                assertEquals(list.size(), 2);
            }
        });
    }

    @Test
    public void postIdentificationAndGetExample2() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                Logger.debug("postIdentificationAndGetExample2");
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addIdentification").post(identification).get(10000).getStatus(), OK);

                JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Hans Maier\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addIdentification").post(identification2).get(10000).getStatus(), OK);

                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
                assertEquals(wsResponse.getStatus(), OK);
                List<Identification> list = getIdentList(wsResponse);
                assertEquals(list.size(), 2);
            }
        });
    }

    @Test
    public void postIdentificationAndGetExample3() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                Logger.debug("postIdentificationAndGetExample3");
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addIdentification").post(identification).get(10000).getStatus(), OK);

                JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Hans Maier\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addIdentification").post(identification2).get(10000).getStatus(), OK);

                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
                assertEquals(wsResponse.getStatus(), OK);
                List<Identification> list = getIdentList(wsResponse);
                assertEquals(list.size(), 2);
            }
        });
    }

    @Test
    public void postIdentificationAndGetExample4() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                Logger.debug("postIdentificationAndGetExample4");
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.80}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addIdentification").post(identification).get(10000).getStatus(), OK);

                JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Hans Maier\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addIdentification").post(identification2).get(10000).getStatus(), OK);

                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
                assertEquals(wsResponse.getStatus(), OK);
                List<Identification> list = getIdentList(wsResponse);
                assertEquals(list.size(), 2);
            }
        });
    }

    private static List<Identification> getIdentList(WSResponse response) {
        List<Identification> list = null;
        try {
            list = Json.mapper().readValue(response.getBody(),
                    TypeFactory.defaultInstance().constructCollectionType(List.class,
                            Identification.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
