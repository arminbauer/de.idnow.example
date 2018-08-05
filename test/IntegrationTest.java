import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Company;
import models.Identification;
import org.junit.Test;
import play.api.db.evolutions.ClassLoaderEvolutionsReader;
import play.db.Database;
import play.db.evolutions.Evolutions;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.test.FakeApplication;
import play.test.Helpers;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class IntegrationTest {
  private static final long DEFAULT_ID = -100L;
  private static final int DEFAULT_TIMEOUT = 10000;

  @Nonnull
  private static ArrayNode createJsonArrayLargerThanAllowedEntitySize() {
    final ArrayNode node = Json.newArray();
    final char[] fiveMbArray = new char[5242880];
    Arrays.fill(fiveMbArray, 'f');
    node.add(new String(fiveMbArray));
    return node;
  }

  @Test
  public void testStartIdentificationReturns400IfRequestIsMalformed() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final JsonNode node = Json.newArray();
      final WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(node).get(DEFAULT_TIMEOUT);
      assertEquals(BAD_REQUEST, response.getStatus());
      assertTrue(response.getBody().startsWith("Can not deserialize instance of models.Identification"));
    });
  }

  @Test
  public void testStartIdentificationReturns400IfBodyIsNull() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post((JsonNode) null).get(DEFAULT_TIMEOUT);
      assertEquals(BAD_REQUEST, response.getStatus());
      assertEquals(response.getBody(), "Provide entity");
    });
  }

  @Test
  public void testAddCompanyReturns400IfRequestIsMalformed() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final JsonNode node = Json.newArray();
      final WSResponse response = WS.url("http://localhost:3333/api/v1/addCompany").post(node).get(DEFAULT_TIMEOUT);
      assertEquals(BAD_REQUEST, response.getStatus());
      assertTrue(response.getBody().startsWith("Can not deserialize instance of models.Company"));
    });
  }

  @Test
  public void testAddCompanyReturns400IfBodyIsNull() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final WSResponse response = WS.url("http://localhost:3333/api/v1/addCompany").post((JsonNode) null).get(DEFAULT_TIMEOUT);
      assertEquals(BAD_REQUEST, response.getStatus());
      assertEquals(response.getBody(), "Provide entity");
    });
  }

  @Test
  public void testAddCompanyReturns413IfBodyIsLargerThan5MB() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final ArrayNode node = createJsonArrayLargerThanAllowedEntitySize();
      final WSResponse response = WS.url("http://localhost:3333/api/v1/addCompany").post(node).get(DEFAULT_TIMEOUT);
      assertEquals(REQUEST_ENTITY_TOO_LARGE, response.getStatus());
    });
  }

  @Test
  public void testGetCompanyReturnsCorrectExistingIdentification() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Company company = TestHelper.buildDefaultCompany(DEFAULT_ID);
      final WSResponse identResponse = WS.url("http://localhost:3333/api/v1/addCompany").post(Json.toJson(company)).get(DEFAULT_TIMEOUT);
      final Long companyId = TestHelper.parseObjectFromResponse(identResponse, Company.class).getId();
      company.setId(companyId);
      final WSResponse response = WS.url("http://localhost:3333/api/v1/companies/" + companyId).get().get(DEFAULT_TIMEOUT);
      assertEquals(OK, response.getStatus());
      assertEquals(company, TestHelper.parseObjectFromResponse(identResponse, Company.class));
    });
  }

  @Test
  public void testStartIdentificationReturns413IfBodyIsLargerThan5MB() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final ArrayNode node = createJsonArrayLargerThanAllowedEntitySize();
      final WSResponse response = WS.url("http://localhost:3333/api/v1/startIdentification").post(node).get(DEFAULT_TIMEOUT);
      assertEquals(REQUEST_ENTITY_TOO_LARGE, response.getStatus());
    });
  }

  @Test
  public void testStartIdentificationReturns404IfCompanyDoesNotExist() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final WSResponse response = createDefaultIdentification(TestHelper.buildDefaultCompany(DEFAULT_ID));
      assertEquals(NOT_FOUND, response.getStatus());
      assertEquals("Company not found", response.getBody());
    });
  }

  @Test
  public void testStartIdentificationReturns400IfCompanyIsNotSet() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final WSResponse response = createDefaultIdentification(null);
      assertEquals(BAD_REQUEST, response.getStatus());
      assertEquals("CompanyId is not set for identification", response.getBody());
    });
  }

  @Test
  public void testGetIdentificationReturns404IfIdentificationDoesNotExist() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final WSResponse response = WS.url("http://localhost:3333/api/v1/identifications/104321").get().get(DEFAULT_TIMEOUT);
      assertEquals(NOT_FOUND, response.getStatus());
    });
  }

  @Test
  public void testGetIdentificationReturnsCorrectExistingIdentification() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Identification identification = TestHelper.buildDefaultIdentification(createAndParseCompanyFromResponse(TestHelper.buildDefaultCompany(DEFAULT_ID)));
      final WSResponse identResponse = WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification)).get(DEFAULT_TIMEOUT);
      final Long identId = TestHelper.parseObjectFromResponse(identResponse, Identification.class).getId();
      identification.setId(identId);
      final WSResponse response = WS.url("http://localhost:3333/api/v1/identifications/" + identId).get().get(DEFAULT_TIMEOUT);
      assertEquals(OK, response.getStatus());
      assertEquals(identification, TestHelper.parseObjectFromResponse(identResponse, Identification.class));
    });
  }

  @Test
  public void testStartIdentificationForExistingCompanyReturns200AndCorrectEntity() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Company createdCompany = createAndParseCompanyFromResponse(TestHelper.buildDefaultCompany(DEFAULT_ID));
      final WSResponse response = createDefaultIdentification(createdCompany);
      assertEquals(OK, response.getStatus());
      assertNotNull(TestHelper.parseObjectFromResponse(response, Identification.class).getId());
    });
  }

  @Test
  public void testAddCompanyReturns200AndCorrectEntity() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final WSResponse response = WS.url("http://localhost:3333/api/v1/addCompany")
                                    .post(Json.toJson(TestHelper.buildDefaultCompany(DEFAULT_ID)))
                                    .get(DEFAULT_TIMEOUT);
      assertEquals(OK, response.getStatus());
      assertNotNull(TestHelper.parseObjectFromResponse(response, Company.class).getId());
    });
  }

  @Test
  public void testGetPendingIdentificationsReturns200AndListOfIdentificationsInCorrectOrder() {
    final FakeApplication app = Helpers.fakeApplication();
    final Database db = app.injector().instanceOf(Database.class);
    running(testServer(3333, app), () -> {
      Evolutions.applyEvolutions(db, ClassLoaderEvolutionsReader.forPrefix("testdatabase/"));
      final WSResponse pendingIdentificationsResponse = WS.url("http://localhost:3333/api/v1/pendingIdentifications").get().get(DEFAULT_TIMEOUT);
      assertEquals(OK, pendingIdentificationsResponse.getStatus());
      final JsonNode pendingIdentifications = pendingIdentificationsResponse.asJson();
      assertTrue(pendingIdentifications instanceof ArrayNode);
      final List<Identification> idents = new ArrayList<>();
      pendingIdentifications.forEach(jsonNode -> idents.add(Json.fromJson(jsonNode, Identification.class)));
      assertEquals(Arrays.asList(2L, 6L, 5L, 3L, 1L, 4L), idents.stream().map(Identification::getId).collect(Collectors.toList()));
      Evolutions.cleanupEvolutions(db);
    });
  }

  @SuppressWarnings("SameParameterValue")
  private WSResponse createIdentification(final Company company, final int waitingTime) {
    final Identification identification = TestHelper.buildIdentification(DEFAULT_ID,
                                                                         company,
                                                                         "default",
                                                                         waitingTime);
    return WS.url("http://localhost:3333/api/v1/startIdentification").post(Json.toJson(identification)).get(DEFAULT_TIMEOUT);
  }

  private WSResponse createDefaultIdentification(final Company createdCompany) {
    return createIdentification(createdCompany, 30);
  }

  private Company createAndParseCompanyFromResponse(final Company company) {
    return TestHelper.parseObjectFromResponse(WS.url("http://localhost:3333/api/v1/addCompany")
                                                .post(Json.toJson(company))
                                                .get(DEFAULT_TIMEOUT),
                                              Company.class);
  }
}