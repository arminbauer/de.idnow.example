import com.fasterxml.jackson.databind.JsonNode;
import data.IdentificationTO;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.CREATED;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class RestControllerTest {

    JsonNode identifications;

    private static final String  ADD_COMPANY_URL = "http://localhost:3333/api/v1/addCompany";

    private static final String START_IDENTIFICATION_URL = "http://localhost:3333/api/v1/startIdentification";

    private static final int TIMEOUT = 10000;

    @Test
    public void getIdentifications () {

        running (testServer (3333, fakeApplication (inMemoryDatabase ())), new Runnable () {
            @Override
            public void run () {

                assertEquals (WS.url ("http://localhost:3333/api/v1/identifications").get ().get (TIMEOUT).getStatus (), OK);
            }
        });

    }

    @Test
    public void postIdentification () {

        running (testServer (3333, fakeApplication (inMemoryDatabase ())), new Runnable () {
            @Override
            public void run () {

                JsonNode company = Json.parse ("{\"id\": 1, \"name\": \"Test Bank\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSlaPercent\": 0.95}");
                assertEquals (WS.url (ADD_COMPANY_URL).post (company).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification = Json.parse ("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waitingTime\": 10, \"companyId\": 1}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification).get (TIMEOUT).getStatus (), CREATED);
            }
        });

    }

    // Exmaple 1
    @Test
    public void getIdentificationByWaitingTime() {

        running (testServer (3333, fakeApplication (inMemoryDatabase ())), new Runnable () {

            @Override
            public void run () {
                JsonNode company = Json.parse ("{\"id\": 1, \"name\": \"Test Bank1\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSlaPercent\": 0.95}");
                assertEquals (WS.url (ADD_COMPANY_URL).post (company).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification = Json.parse ("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waitingTime\": 30, \"companyId\": 1}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification2 = Json.parse ("{\"id\": 2, \"name\": \"Peter Huber2\", \"time\": 1435667216, \"waitingTime\": 40, \"companyId\": 1}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification2).get (TIMEOUT).getStatus (), CREATED);

                WSResponse wsResponse = WS.url ("http://localhost:3333/api/v1/identifications").get ().get (TIMEOUT);
                assertEquals (wsResponse.getStatus (), OK);
                JsonNode jsonNode = wsResponse.asJson ();
                assertEquals (jsonNode.size (), 2);
                IdentificationTO id1 = Json.fromJson (jsonNode.get (0), IdentificationTO.class);
                assertEquals (id1.getId ().longValue (), 2L);
            }
        } );

    }


    // Example 2
    @Test
    public void getIdentificationBySLAUrgency() {

        running (testServer (3333, fakeApplication (inMemoryDatabase ())), new Runnable () {

            @Override
            public void run () {

                JsonNode company = Json.parse ("{\"id\": 1, \"name\": \"Test Bank1\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSlaPercent\": 0.95}");
                assertEquals (WS.url (ADD_COMPANY_URL).post (company).get (TIMEOUT).getStatus (), CREATED);

                JsonNode company2 = Json.parse ("{\"id\": 2, \"name\": \"Test Bank2\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSlaPercent\": 0.90}");
                assertEquals (WS.url (ADD_COMPANY_URL).post (company2).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification = Json.parse ("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waitingTime\": 30, \"companyId\": 1}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification2 = Json.parse ("{\"id\": 2, \"name\": \"Peter Huber2\", \"time\": 1435667216, \"waitingTime\": 30, \"companyId\": 2}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification2).get (TIMEOUT).getStatus (), CREATED);


                WSResponse wsResponse = WS.url ("http://localhost:3333/api/v1/identifications").get ().get (TIMEOUT);
                assertEquals (wsResponse.getStatus (), OK);
                JsonNode jsonNode = wsResponse.asJson ();
                assertEquals (jsonNode.size (), 2);
                IdentificationTO id1 = Json.fromJson (jsonNode.get (0), IdentificationTO.class);
                assertEquals (id1.getId ().longValue (), 2L);
                IdentificationTO id2 = Json.fromJson (jsonNode.get (1), IdentificationTO.class);
                assertEquals (id2.getId ().longValue (), 1L);
            }
        });
    }


    // Example 3
    @Test
    public void getIdentificationBySLATimeUrgency () {

        running (testServer (3333, fakeApplication (inMemoryDatabase ())), new Runnable () {
            @Override
            public void run () {

                JsonNode company = Json.parse ("{\"id\": 1, \"name\": \"Test Bank1\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSlaPercent\": 0.95}");
                assertEquals (WS.url (ADD_COMPANY_URL).post (company).get (TIMEOUT).getStatus (), CREATED);


                JsonNode company2 = Json.parse ("{\"id\": 2, \"name\": \"Test Bank2\", \"slaTime\": 120, \"slaPercentage\": 0.8, \"currentSlaPercent\": 0.95}");
                assertEquals (WS.url (ADD_COMPANY_URL).post (company2).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification = Json.parse ("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waitingTime\": 30, \"companyId\": 1}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification2 = Json.parse ("{\"id\": 2, \"name\": \"Peter Huber2\", \"time\": 1435667216, \"waitingTime\": 30, \"companyId\": 2}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification2).get (TIMEOUT).getStatus (), CREATED);


                WSResponse wsResponse = WS.url ("http://localhost:3333/api/v1/identifications").get ().get (TIMEOUT);
                assertEquals (wsResponse.getStatus (), OK);
                JsonNode jsonNode = wsResponse.asJson ();
                assertEquals (jsonNode.size (), 2);
                IdentificationTO id1 = Json.fromJson (jsonNode.get (0), IdentificationTO.class);
                assertEquals (id1.getId ().longValue (), 1L);
                IdentificationTO id2 = Json.fromJson (jsonNode.get (1), IdentificationTO.class);
                assertEquals (id2.getId ().longValue (), 2L);
            }
        });
    }

    // Example 4: Here it is assumed that the difference between SLATime and WaitingTime has more priority.

    @Test
    public void getIdentificationByWaitingTimeUrgency() {

        running (testServer (3333, fakeApplication (inMemoryDatabase ())), new Runnable () {

            @Override
            public void run () {

                JsonNode company = Json.parse ("{\"id\": 1, \"name\": \"Test Bank1\", \"slaTime\": 60, \"slaPercentage\": 0.9, \"currentSlaPercent\": 0.95}");
                assertEquals (WS.url (ADD_COMPANY_URL).post (company).get (TIMEOUT).getStatus (), CREATED);

                JsonNode company2 = Json.parse ("{\"id\": 2, \"name\": \"Test Bank2\", \"slaTime\": 120, \"slaPercentage\": 0.8, \"currentSlaPercent\": 0.80}");
                assertEquals (WS.url (ADD_COMPANY_URL).post (company2).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification = Json.parse ("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waitingTime\": 45, \"companyId\": 1}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification).get (TIMEOUT).getStatus (), CREATED);

                JsonNode identification2 = Json.parse ("{\"id\": 2, \"name\": \"Peter Huber2\", \"time\": 1435667216, \"waitingTime\": 30, \"companyId\": 2}");
                assertEquals (WS.url (START_IDENTIFICATION_URL).post (identification2).get (TIMEOUT).getStatus (), CREATED);


                WSResponse wsResponse = WS.url ("http://localhost:3333/api/v1/identifications").get ().get (TIMEOUT);
                assertEquals (wsResponse.getStatus (), OK);
                JsonNode jsonNode = wsResponse.asJson ();
                assertEquals (jsonNode.size (), 2);
                IdentificationTO id1 = Json.fromJson (jsonNode.get (0), IdentificationTO.class);
                assertEquals (id1.getId ().longValue (), 1L);
                IdentificationTO id2 = Json.fromJson (jsonNode.get (1), IdentificationTO.class);
                assertEquals (id2.getId ().longValue (), 2L);

            }
        });

    }

}
