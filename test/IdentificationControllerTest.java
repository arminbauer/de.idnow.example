import models.Company;
import models.Identification;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;

public class IdentificationControllerTest {

  @Test
  public void getIdentifications() {
  }

  @Test
  public void postIdentification() {
    final Company company = new Company();
    company.setId("1");
    company.setName("Test Bank");
    company.setSlaTimeInSeconds(60);
    company.setSlaPercentage(0.9f);
    company.setCurrentSlaPercentage(0.95f);
    assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company)).get(10000).getStatus(), OK);
    final Identification identification = new Identification();
    identification.setCompany(company);
    identification.setId(1L);
    identification.setUsername("Peter Huber");
    identification.setStartedAt(Instant.ofEpochSecond(1435667215L));
    assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification)).get(10000).getStatus(), OK);
  }
}
