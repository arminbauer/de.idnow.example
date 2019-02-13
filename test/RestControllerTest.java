import com.fasterxml.jackson.databind.JsonNode;
import model.Company;
import model.Identification;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import service.DBService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class RestControllerTest {

    JsonNode identifications;

    @Test
    public void postIdentification() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
            }
        });
    }


    @Test
    public void postCompany() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
            }
        });
    }


    @Test
    public void getIdentifications() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/identifications").get().get(10000);
                assertEquals(wsResponse.getStatus(), OK);
                Logger.debug("GET http://localhost:3333/api/v1/identifications returned: " + wsResponse.getBody());
            }
        });
    }

    /* Example 1:
    /*   One company with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
    /*   Identification 1: Waiting_time=30
    /*   Identification 2: Waiting_time=45
    /* Expected order: Identification 2, Identification 1 (since Ident 2 has waited longer)
    * */
    @Test
    public void getPendingIdentificationsTest1() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                DBService service = DBService.getInstance();

                Company comp1 = new Company("1", "IDnow", 60, 0.9, 0.95);
                service.addToCompanyDB(comp1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse("2019-02-12");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String dateExpected = "Feb 12, 2019 12:00:00 AM";

                Identification identi = new Identification("1", "IDnow", date, 30, "1");
                Identification identi2 = new Identification("2", "IDnow", date, 45, "1");
                service.addToIdentDB(identi);
                service.addToIdentDB(identi2);
                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
                String expected = "[{\"id\":\"2\",\"name\":\"IDnow\",\"time\":\"" + dateExpected + "\",\"waiting_time\":45,\"companyid\":\"1\"},{\"id\":\"1\",\"name\":\"IDnow\",\"time\":\"" + dateExpected + "\",\"waiting_time\":30,\"companyid\":\"1\"}]";
                assertEquals(wsResponse.getBody(), expected);
                Logger.debug("getPendingIdentificationsTest1 returned: " + wsResponse.getBody());
                DBService.getInstance().eraseDB();
            }
        });
    }


    /* Example 2:
    /*   Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
    /*   Company 2 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.90
    /*   Identification 1 belonging to Company1: Waiting_time=30
    /* Identification 2 belonging to Company2: Waiting_time=30
    /* Expected order: Identification 2, Identification 1 (since Company 2 already has a lower current SLA percentage in this month, so its identifications have higher prio)
    * */
    @Test
    public void getPendingIdentificationsTest2() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                DBService service = DBService.getInstance();

                Company comp1 = new Company("1", "IDnow", 60, 0.9, 0.95);
                Company comp2 = new Company("2", "Dummy Company", 60, 0.9, 0.90);
                service.addToCompanyDB(comp1);
                service.addToCompanyDB(comp2);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse("2019-02-12");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String dateExpected = "Feb 12, 2019 12:00:00 AM";

                Identification identi = new Identification("1", "IDnow", date, 30, "1");
                Identification identi2 = new Identification("2", "IDnow", date, 30, "2");
                service.addToIdentDB(identi);
                service.addToIdentDB(identi2);
                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
                //String expected = "";
                //assertEquals(wsResponse.getBody(), expected);

                //TODO - add proper assert instead of OK response confirmation, for now manually verifying with Logger.debug
                assertEquals(wsResponse.getStatus(), OK);
                Logger.debug("getPendingIdentificationsTest2 returned: " + wsResponse.getBody());
                DBService.getInstance().eraseDB();
            }
        });
    }


    /*     Example 3:
    /*        - Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
    /*        - Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.95
    /*        - Identification 1 belonging to Company1: Waiting_time=30
    /*        - Identification 2 belonging to Company2: Waiting_time=30
    /*      Expected order: Identification 1, Identification 2 (since company 1 has a lower, more urgent SLA)
    * */
    @Test
    public void getPendingIdentificationsTest3() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                DBService service = DBService.getInstance();

                Company comp1 = new Company("1", "IDnow", 60, 0.9, 0.95);
                Company comp2 = new Company("2", "Dummy Company", 120, 0.8, 0.95);
                service.addToCompanyDB(comp1);
                service.addToCompanyDB(comp2);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse("2019-02-12");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String dateExpected = "Feb 12, 2019 12:00:00 AM";

                Identification identi = new Identification("1", "IDnow", date, 30, "1");
                Identification identi2 = new Identification("2", "IDnow", date, 30, "2");
                service.addToIdentDB(identi);
                service.addToIdentDB(identi2);
                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
                //String expected = "";
                //assertEquals(wsResponse.getBody(), expected);

                //TODO - add proper assert instead of OK response confirmation, for now manually verifying with Logger.debug
                assertEquals(wsResponse.getStatus(), OK);
                Logger.debug("getPendingIdentificationsTest3 returned: " + wsResponse.getBody());
                DBService.getInstance().eraseDB();
            }
        });
    }


    /*     Example 4:
    /*     - Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
    /*     - Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.80
    /*     - Identification 1 belonging to Company1: Waiting_time=45
    /*     - Identification 2 belonging to Company2: Waiting_time=30
    * */
    @Test
    public void getPendingIdentificationsTest4() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                DBService service = DBService.getInstance();

                Company comp1 = new Company("1", "IDnow", 60, 0.9, 0.95);
                Company comp2 = new Company("2", "Dummy Company", 120, 0.8, 0.80);
                service.addToCompanyDB(comp1);
                service.addToCompanyDB(comp2);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse("2019-02-12");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String dateExpected = "Feb 12, 2019 12:00:00 AM";

                Identification identi = new Identification("1", "IDnow", date, 45, "1");
                Identification identi2 = new Identification("2", "IDnow", date, 30, "2");
                service.addToIdentDB(identi);
                service.addToIdentDB(identi2);
                WSResponse wsResponse = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000);
                //String expected = "";
                //assertEquals(wsResponse.getBody(), expected);

                //TODO - add proper assert instead of OK response confirmation, for now manually verifying with Logger.debug
                assertEquals(wsResponse.getStatus(), OK);
                Logger.debug("getPendingIdentificationsTest4 returned: " + wsResponse.getBody());
                DBService.getInstance().eraseDB();
            }
        });
    }


}
