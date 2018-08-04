import models.Company;
import models.Identification;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class CompanyControllerTest {

  @Test
  public void getCompanies() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())),
            () -> assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus(), OK));

  }

  @Test
  public void createCompany() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Company company = new Company();
      company.setId("1");
      company.setName("Test Bank");
      company.setSlaTimeInSeconds(60);
      company.setSlaPercentage(0.9f);
      company.setCurrentSlaPercentage(0.95f);
      assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company)).get(10000).getStatus(), OK);
    });
  }

}
