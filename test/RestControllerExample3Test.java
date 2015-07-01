import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import javafx.util.Pair;
import model.Identification;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Created by sleski on 30.06.2015.
 */
public class RestControllerExample3Test extends MainTest{

    @Before
    public void destroy() {
        super.destroy();
    }

    @Test
    public void example2() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

                company = Json.parse("{\"id\": 2, \"name\": \"Test Bank\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                // First - Expected order: Identification 1, Identification 2 (since company 1 has a lower, more urgent SLA)
                identification = Json.parse("{\"id\": 2, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);


                F.Promise<Pair<JsonNode,Integer>> pairPromise = WS.url("http://localhost:3333/api/v1/identifications").get().map(
                        new F.Function<WSResponse, Pair<JsonNode,Integer>>() {
                            public Pair<JsonNode,Integer> apply(WSResponse response) {
                                Integer status=response.getStatus();
                                JsonNode json = response.asJson();
                                return new Pair(json,status);
                            }
                        }
                );

                Pair<JsonNode,Integer> pair = pairPromise.get(1000);
                JsonNode jsonNode = pair.getKey();
                System.out.println("jsond = " + jsonNode);
                int status = pair.getValue();

                assertEquals(status, OK);

                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Identification> identificationsWithOrder =  mapper.readValue(jsonNode.toString(),
                            TypeFactory.defaultInstance().constructCollectionType(List.class, Identification.class));
                    Identification first = identificationsWithOrder.get(0);
                    assertEquals(first.getId(),1);
                } catch (JsonParseException e) {
                    Logger.error("problem occured during json object parsing", e);
                } catch (IOException e) {
                    Logger.error("problem occured during json object parsing", e);
                }


            }
        });
    }
}
