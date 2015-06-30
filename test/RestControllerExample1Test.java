import com.fasterxml.jackson.databind.JsonNode;
import data.AppData;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Created by sleski on 30.06.2015.
 */
public class RestControllerExample1Test extends MainTest{

    @Before
    public void destroy() {
        super.destroy();
    }

    @Test
    @Ignore
    public void example1() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

                identification = Json.parse("{\"id\": 2, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 20, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);



            }
        });
    }
}
