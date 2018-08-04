import com.fasterxml.jackson.databind.JsonNode;
import models.Company;
import models.Identification;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class IntegrationTest {
  @Test
  public void testWelcomePage() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
      browser.goTo("http://localhost:3333");
      assertTrue(browser.pageSource().contains("Your new application is ready."));
    });
  }

  @Test
  public void testStartIdentificationReturns400IfRequestIsMalformed() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final JsonNode node = Json.newArray();
      final WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(node).get(10000);
      assertEquals(BAD_REQUEST, response.getStatus());
      assertTrue(response.getBody().startsWith("Can not deserialize instance of models.Identification"));
    });
  }

  @Test
  public void testAddCompanyReturns400IfRequestIsMalformed() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final JsonNode node = Json.newArray();
      final WSResponse response = WS.url("http://localhost:3333/api/v1/addCompany").post(node).get(10000);
      assertEquals(BAD_REQUEST, response.getStatus());
      assertTrue(response.getBody().startsWith("Can not deserialize instance of models.Company"));
    });
  }

  @Test
  public void testStartIdentificationReturns404IfCompanyDoesNotExist() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Identification identification = new Identification();
      identification.setId(-100L);
      identification.setCompanyId(-100L);
      identification.setUsername("Peter Huber");
      identification.setStartedAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(1435667215L), ZoneId.of("UTC")));
      final WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification)).get(10000);
      assertEquals(NOT_FOUND, response.getStatus());
      assertEquals("Company not found", response.getBody());
    });
  }

  @Test
  public void testStartIdentificationReturns400IfCompanyIsNotSet() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Identification identification = new Identification();
      identification.setId(-100L);
      identification.setUsername("Peter Huber");
      identification.setStartedAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(1435667215L), ZoneId.of("UTC")));
      final WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification)).get(10000);
      assertEquals(BAD_REQUEST, response.getStatus());
      assertEquals("CompanyId is not set for identification", response.getBody());
    });
  }

  @Test
  public void testStartIdentificationForExistingCompanyReturns200AndCorrectEntity() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Company createdCompany = TestHelper.parseObjectFromResponse(WS.url("http://localhost:3333/api/v1/addCompany")
                                                                          .post(Json.toJson(buildCompany()))
                                                                          .get(10000L),
                                                                        Company.class);
      final Identification identification = new Identification();
      identification.setCompanyId(createdCompany.getId());
      identification.setUsername("Peter Huber");
      identification.setStartedAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(1435667215L), ZoneId.of("UTC")));
      final WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification)).get(10000);
      assertEquals(OK, response.getStatus());
      assertNotNull(TestHelper.parseObjectFromResponse(response, Identification.class).getId());
    });
  }

  @Test
  public void testAddCompanyReturns200AndCorrectEntity() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final WSResponse response = WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(buildCompany())).get(10000L);
      assertEquals(OK, response.getStatus());
      assertNotNull(TestHelper.parseObjectFromResponse(response, Company.class).getId());
    });
  }

  @NotNull
  private Company buildCompany() {
    final Company company = new Company();
    company.setId(-100L);
    company.setName("Test Bank");
    company.setSlaTimeInSeconds(60);
    company.setSlaPercentage(0.9f);
    company.setCurrentSlaPercentage(0.95f);
    return company;
  }
}