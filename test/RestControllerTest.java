import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import models.Identification;
import play.libs.Json;
import play.libs.ws.WS;

/**
 * Simple (JUnit) tests that can call all parts of a play app. If you are interested in mocking a whole application, see
 * the wiki for more details.
 */
public class RestControllerTest
{
  JsonNode identifications;

  @Test
  public void testGetPendingIdentifications()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        assertEquals(WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).getStatus(), OK);
      }
    });
  }

  @Test
  public void testAddCompany()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);
      }
    });
  }

  @Test
  public void testAddCompanyWithIdAlreadyUsed()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode company1 = Json.parse("{\"id\": 1, \"name\": \"Test Bank 1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        JsonNode company2 = Json.parse("{\"id\": 1, \"name\": \"Test Bank 2\", \"sla_time\": 90, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.9}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), BAD_REQUEST);
      }
    });
  }

  @Test
  public void testStartIdentification()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
      }
    });
  }

  @Test
  public void testStartIdentificationWithIdAlreadyUsed()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
        JsonNode identification2 = Json.parse("{\"id\": 1, \"name\": \"Petra Meier\", \"time\": 1435667214, \"waiting_time\": 15, \"companyid\": 2}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), BAD_REQUEST);
      }
    });
  }

  @Test
  public void testAddCompanyAndPostIdentification()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

        JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
      }
    });
  }

  @Test
  /**
   * One company with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95 Identification 1: Waiting_time=30
   * Identification 2: Waiting_time=45 Expected order: Identification 2, Identification 1 (since Ident 2 has waited
   * longer)
   */
  public void testExample1()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Company\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

        JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Max Mustermann\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

        JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Erika Mustermann\", \"time\": 1435667210, \"waiting_time\": 45, \"companyid\": 1}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

        JsonNode json = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
        assertEquals(2, json.size());
        Identification ident1 = Json.fromJson(json.get(0), Identification.class);
        Identification ident2 = Json.fromJson(json.get(1), Identification.class);
        assertEquals(2, ident1.id);
        assertEquals(1, ident2.id);
      }
    });
  }

  @Test
  /**
   * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95 Company 2 with SLA_time=60,
   * SLA_percentage=0.9, Current_SLA_percentage=0.90 Identification 1 belonging to Company1: Waiting_time=30
   * Identification 2 belonging to Company2: Waiting_time=30 Expected order: Identification 2, Identification 1 (since
   * Company 2 already has a lower current SLA percentage in this month, so its identifications have higher prio)
   */
  public void testExample2()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode company1 = Json
          .parse("{\"id\": 1, \"name\": \"Test Company 1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

        JsonNode company2 = Json.parse("{\"id\": 2, \"name\": \"Test Company 2\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.9}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

        JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Max Mustermann\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

        JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Erika Mustermann\", \"time\": 1435667210, \"waiting_time\": 30, \"companyid\": 2}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

        JsonNode json = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
        assertEquals(2, json.size());
        Identification ident1 = Json.fromJson(json.get(0), Identification.class);
        Identification ident2 = Json.fromJson(json.get(1), Identification.class);
        assertEquals(2, ident1.id);
        assertEquals(1, ident2.id);
      }
    });
  }

  @Test
  /**
   * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95 Company 2 with SLA_time=120,
   * SLA_percentage=0.8, Current_SLA_percentage=0.95 Identification 1 belonging to Company1: Waiting_time=30
   * Identification 2 belonging to Company2: Waiting_time=30 Expected order: Identification 1, Identification 2 (since
   * company 1 has a lower, more urgent SLA time)
   */
  public void testExample3()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode company1 = Json
          .parse("{\"id\": 1, \"name\": \"Test Company 1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

        JsonNode company2 = Json
          .parse("{\"id\": 2, \"name\": \"Test Company 2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.95}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

        JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Max Mustermann\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 1}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

        JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Erika Mustermann\", \"time\": 1435667210, \"waiting_time\": 30, \"companyid\": 2}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

        JsonNode json = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
        assertEquals(2, json.size());
        Identification ident1 = Json.fromJson(json.get(0), Identification.class);
        Identification ident2 = Json.fromJson(json.get(1), Identification.class);
        assertEquals(1, ident1.id);
        assertEquals(2, ident2.id);
      }
    });
  }

  @Test
  /**
   * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95 Company 2 with SLA_time=120,
   * SLA_percentage=0.8, Current_SLA_percentage=0.80 Identification 1 belonging to Company1: Waiting_time=45
   * Identification 2 belonging to Company2: Waiting_time=30 What is the expected order here? Expected order:
   * Identification 2, Identification 1 (since current SLA percentage of company 2 has to be improved)
   */
  public void testExample4()
  {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
      @Override
      public void run()
      {
        JsonNode company1 = Json
          .parse("{\"id\": 1, \"name\": \"Test Company 1\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company1).get(10000).getStatus(), OK);

        JsonNode company2 = Json
          .parse("{\"id\": 2, \"name\": \"Test Company 2\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.8}");
        assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company2).get(10000).getStatus(), OK);

        JsonNode identification1 = Json.parse("{\"id\": 1, \"name\": \"Max Mustermann\", \"time\": 1435667215, \"waiting_time\": 45, \"companyid\": 1}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification1).get(10000).getStatus(), OK);

        JsonNode identification2 = Json.parse("{\"id\": 2, \"name\": \"Erika Mustermann\", \"time\": 1435667210, \"waiting_time\": 30, \"companyid\": 2}");
        assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification2).get(10000).getStatus(), OK);

        JsonNode json = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(10000).asJson();
        assertEquals(2, json.size());
        Identification ident1 = Json.fromJson(json.get(0), Identification.class);
        Identification ident2 = Json.fromJson(json.get(1), Identification.class);
        assertEquals(2, ident1.id);
        assertEquals(1, ident2.id);
      }
    });
  }

}
