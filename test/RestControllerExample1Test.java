import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import data.AppData;
import javafx.util.Pair;
import model.Identification;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.libs.F.Promise;

/**
 * Created by sleski on 30.06.2015.
 */
public class RestControllerExample1Test extends MainTest{

    @Before
    public void destroy() {
        super.destroy();
    }

    @Test
    public void example1() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                // "waiting_time": 20 - should be first
                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

                // "waiting_time": 10
                identification = Json.parse("{\"id\": 2, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000)
                        .getStatus(), OK);


                Promise<Pair<JsonNode,Integer>> pairPromise = WS.url("http://localhost:3333/api/v1/identifications").get().map(
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
                int status = pair.getValue();

                assertEquals(status, OK);

                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Identification> identificationsWithOrder =  mapper.readValue(jsonNode.toString(),
                            TypeFactory.defaultInstance().constructCollectionType(List.class, Identification.class));
                    Identification first = identificationsWithOrder.get(0);
                    assertEquals(first.getId(),2);
                } catch (JsonParseException e) {
                    Logger.error("problem occured during json object parsing", e);
                } catch (IOException e) {
                    Logger.error("problem occured during json object parsing", e);
                }
            }
        });
    }
}
