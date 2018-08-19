import com.fasterxml.jackson.databind.JsonNode;
import models.Company;
import models.Identification;
import org.junit.Test;
import play.libs.F.Callback;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.test.TestBrowser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

public class IntegrationTest {

    private static final String ADD_COMPANY_URL = "http://localhost:3333/api/v1/addCompany";
    private static final String START_IDENTIFICATION_URL = "http://localhost:3333/api/v1/startIdentification";
    private static final String IDENTIFICATIONS_URL = "http://localhost:3333/api/v1/identifications";

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertTrue(browser.pageSource().contains("Your new application is ready."));
            }
        });
    }

    @Test
    public void testScenario1() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            Company company = new Company.Builder()
                    .id(1)
                    .name("Company-1")
                    .slaTime(60)
                    .slaPercentage(0.9)
                    .currentSLAPercentage(0.95)
                    .get();

            assertEquals(WS.url(ADD_COMPANY_URL).post(company.toJson()).get(10000).getStatus(), OK);

            Identification ident1 = new Identification.Builder()
                    .id(1)
                    .name("A")
                    .time(System.currentTimeMillis())
                    .waitingTime(30)
                    .companyId(company.getId())
                    .get();


            Identification ident2 = new Identification.Builder()
                    .id(2)
                    .name("B")
                    .time(System.currentTimeMillis())
                    .waitingTime(45)
                    .companyId(company.getId())
                    .get();

            assertEquals(WS.url(START_IDENTIFICATION_URL).post(ident1.toJson()).get(10000).getStatus(), OK);
            assertEquals(WS.url(START_IDENTIFICATION_URL).post(ident2.toJson()).get(10000).getStatus(), OK);

            WSResponse wsResponse = WS.url(IDENTIFICATIONS_URL).get().get(10000);
            assertEquals(wsResponse.getStatus(), OK);

            JsonNode jsonNode = wsResponse.asJson();
            assertEquals(Identification.fromJson(jsonNode.get(0)).getId(), ident2.getId());
            assertEquals(Identification.fromJson(jsonNode.get(1)).getId(), ident1.getId());
        });
    }

    @Test
    public void testScenario2() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            Company company1 = new Company.Builder()
                    .id(1)
                    .name("Company-1")
                    .slaTime(60)
                    .slaPercentage(0.9)
                    .currentSLAPercentage(0.95)
                    .get();

            Company company2 = new Company.Builder()
                    .id(2)
                    .name("Company-2")
                    .slaTime(60)
                    .slaPercentage(0.9)
                    .currentSLAPercentage(0.90)
                    .get();


            assertEquals(WS.url(ADD_COMPANY_URL).post(company1.toJson()).get(10000).getStatus(), OK);
            assertEquals(WS.url(ADD_COMPANY_URL).post(company2.toJson()).get(10000).getStatus(), OK);

            Identification ident1 = new Identification.Builder()
                    .id(1)
                    .name("A")
                    .time(System.currentTimeMillis())
                    .waitingTime(30)
                    .companyId(company1.getId())
                    .get();


            Identification ident2 = new Identification.Builder()
                    .id(2)
                    .name("B")
                    .time(System.currentTimeMillis())
                    .waitingTime(30)
                    .companyId(company2.getId())
                    .get();

            assertEquals(WS.url(START_IDENTIFICATION_URL).post(ident1.toJson()).get(10000).getStatus(), OK);
            assertEquals(WS.url(START_IDENTIFICATION_URL).post(ident2.toJson()).get(10000).getStatus(), OK);

            WSResponse wsResponse = WS.url(IDENTIFICATIONS_URL).get().get(10000);
            assertEquals(wsResponse.getStatus(), OK);

            JsonNode jsonNode = wsResponse.asJson();
            assertEquals(Identification.fromJson(jsonNode.get(0)).getId(), ident2.getId());
            assertEquals(Identification.fromJson(jsonNode.get(1)).getId(), ident1.getId());
        });
    }

    @Test
    public void testScenario3() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            Company company1 = new Company.Builder()
                    .id(1)
                    .name("Company-1")
                    .slaTime(60)
                    .slaPercentage(0.9)
                    .currentSLAPercentage(0.95)
                    .get();

            Company company2 = new Company.Builder()
                    .id(2)
                    .name("Company-2")
                    .slaTime(120)
                    .slaPercentage(0.8)
                    .currentSLAPercentage(0.95)
                    .get();


            assertEquals(WS.url(ADD_COMPANY_URL).post(company1.toJson()).get(10000).getStatus(), OK);
            assertEquals(WS.url(ADD_COMPANY_URL).post(company2.toJson()).get(10000).getStatus(), OK);

            Identification ident1 = new Identification.Builder()
                    .id(1)
                    .name("A")
                    .time(System.currentTimeMillis())
                    .waitingTime(30)
                    .companyId(company1.getId())
                    .get();


            Identification ident2 = new Identification.Builder()
                    .id(2)
                    .name("B")
                    .time(System.currentTimeMillis())
                    .waitingTime(30)
                    .companyId(company2.getId())
                    .get();

            assertEquals(WS.url(START_IDENTIFICATION_URL).post(ident1.toJson()).get(10000).getStatus(), OK);
            assertEquals(WS.url(START_IDENTIFICATION_URL).post(ident2.toJson()).get(10000).getStatus(), OK);

            WSResponse wsResponse = WS.url(IDENTIFICATIONS_URL).get().get(10000);
            assertEquals(wsResponse.getStatus(), OK);

            JsonNode jsonNode = wsResponse.asJson();
            assertEquals(Identification.fromJson(jsonNode.get(0)).getId(), ident1.getId());
            assertEquals(Identification.fromJson(jsonNode.get(1)).getId(), ident2.getId());
        });
    }

    @Test
    public void testScenario4() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
            Company company1 = new Company.Builder()
                    .id(1)
                    .name("Company-1")
                    .slaTime(60)
                    .slaPercentage(0.9)
                    .currentSLAPercentage(0.95)
                    .get();

            Company company2 = new Company.Builder()
                    .id(2)
                    .name("Company-2")
                    .slaTime(120)
                    .slaPercentage(0.8)
                    .currentSLAPercentage(0.80)
                    .get();


            assertEquals(WS.url(ADD_COMPANY_URL).post(company1.toJson()).get(10000).getStatus(), OK);
            assertEquals(WS.url(ADD_COMPANY_URL).post(company2.toJson()).get(10000).getStatus(), OK);

            Identification ident1 = new Identification.Builder()
                    .id(1)
                    .name("A")
                    .time(System.currentTimeMillis())
                    .waitingTime(45)
                    .companyId(company1.getId())
                    .get();


            Identification ident2 = new Identification.Builder()
                    .id(2)
                    .name("B")
                    .time(System.currentTimeMillis())
                    .waitingTime(30)
                    .companyId(company2.getId())
                    .get();

            assertEquals(WS.url(START_IDENTIFICATION_URL).post(ident1.toJson()).get(10000).getStatus(), OK);
            assertEquals(WS.url(START_IDENTIFICATION_URL).post(ident2.toJson()).get(10000).getStatus(), OK);

            WSResponse wsResponse = WS.url(IDENTIFICATIONS_URL).get().get(10000);
            assertEquals(wsResponse.getStatus(), OK);

            JsonNode jsonNode = wsResponse.asJson();
            assertEquals(Identification.fromJson(jsonNode.get(0)).getId(), ident1.getId());
            assertEquals(Identification.fromJson(jsonNode.get(1)).getId(), ident2.getId());
        });
    }

}
