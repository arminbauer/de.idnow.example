import models.Company;
import models.Identification;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class IdentificationControllerTest {

  @Test
  public void getIdentifications() {
  }

  @Test
  public void postIdentification() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Identification identification = new Identification();
      identification.setCompany(createCompany());
      identification.setId(1L);
      identification.setUsername("Peter Huber");
      identification.setStartedAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(1435667215L), ZoneId.of("UTC")));
      assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification)).get(10000).getStatus(), OK);
    });
  }

  private Company createCompany() {
    final Company company = new Company();
    company.setId(1L);
    company.setName("Test Bank");
    company.setSlaTimeInSeconds(60);
    company.setSlaPercentage(0.9f);
    company.setCurrentSlaPercentage(0.95f);
    Json.fromJson(Json.parse(WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company)).get(10000).getBody()), Company.class);
    return company;
  }
}
