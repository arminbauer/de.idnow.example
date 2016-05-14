import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import dao.CompanyStore;
import models.Company;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.libs.Json;
import play.libs.ws.WSResponse;
import play.test.Helpers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Created by nick on 14.05.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CompaniesControllerTest extends AbstractTest {
    @Mock
    private CompanyStore companyStore;

    @Before
    public void setUp() throws Exception {
        GuiceApplicationBuilder builder = new GuiceApplicationLoader().
                builder(new GuiceApplicationLoader.Context(Environment.simple())).bindings(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CompanyStore.class).toInstance(companyStore);
            }
        });
        Guice.createInjector(builder.applicationModule()).injectMembers(this);
        Helpers.start(application);

    }

    @Test
    public void should_create_new_company() {

        running(testServer(port, application), () -> {
            Company requestBody = new Company();

            requestBody.setName(RandomStringUtils.randomAlphabetic(12));
            requestBody.setSlaTimeSeconds(RandomUtils.nextInt(50, 200));
            requestBody.setSlaPercentage(RandomUtils.nextFloat(0.80F, 0.95F));
            requestBody.setCurrentSlaPercentage(RandomUtils.nextFloat(0.80F, 0.95F));

            Company saveCompany = SerializationUtils.clone(requestBody);
            saveCompany.setId(RandomUtils.nextLong(0, Long.MAX_VALUE));

            Mockito.doReturn(saveCompany).when(companyStore).upsert(Mockito.any(Company.class));

            WSResponse createCompanyResponse = localUrl("/api/v1/companies").setContentType("application/json").post(Json.toJson(requestBody)).get(10_000);
            JsonNode body = createCompanyResponse.asJson();
            assertThat(createCompanyResponse.getStatus(), is(CREATED));

            assertThat("Id should be got back", body.findValue("id").asLong(), is(saveCompany.getId()));

            ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
            Mockito.verify(companyStore).upsert(captor.capture());
            assertThat(captor.getValue().getName(), Matchers.is(requestBody.getName()));
            assertThat(captor.getValue().getSlaPercentage(), Matchers.is(requestBody.getSlaPercentage()));
            assertThat(captor.getValue().getSlaTimeSeconds(), Matchers.is(requestBody.getSlaTimeSeconds()));
            assertThat(captor.getValue().getCurrentSlaPercentage(), Matchers.is(requestBody.getCurrentSlaPercentage()));
        });

    }

    @Test
    public void should_not_create_new_company_when_validation_fails() {
        running(testServer(port, application), () -> {
            WSResponse createCompanyResponse = localUrl("/api/v1/companies").setContentType("application/json").post(Json.toJson(new Company())).get(10_000);
            assertEquals(createCompanyResponse.getStatus(), BAD_REQUEST);
            JsonNode body = createCompanyResponse.asJson();
            Assert.assertThat(body.get("errorType").asText(), is("validationFailed"));
            Assert.assertThat(body.get("details").isArray(), is(true));
            Assert.assertThat(body.get("details").size(), is(1));
            Mockito.verifyZeroInteractions(companyStore);
        });

    }


    @Test
    public void should_reject_upsert_when_id_not_null() {
        Company companyToSave = RandomObjects.randomCompany();
        assertThat(companyToSave.getId(), notNullValue());
        running(testServer(port, application), () -> {
            WSResponse createCompanyResponse = localUrl("/api/v1/companies").setContentType("application/json").post(Json.toJson(companyToSave)).get(10_000);
            assertEquals(createCompanyResponse.getStatus(), BAD_REQUEST);
            Mockito.verifyZeroInteractions(companyStore);
        });

    }

    @Test
    public void should_return_all_companies() {
        Company company1 = RandomObjects.randomCompany();
        Company company2 = RandomObjects.randomCompany();
        Company company3 = RandomObjects.randomCompany();

        running(testServer(port, application), () -> {
            Mockito.doReturn(Lists.newArrayList(company1, company2, company3)).when(companyStore).findAll();

            WSResponse response = localUrl("/api/v1/companies").get().get(10_000);
            assertEquals(response.getStatus(), OK);
            JsonNode body = response.asJson();
            assertThat(body.isArray(), is(true));
            assertThat(body.get(0).findValue("id").asLong(), is(company1.getId()));
            assertThat(body.get(1).findValue("id").asLong(), is(company2.getId()));
            assertThat(body.get(2).findValue("id").asLong(), is(company3.getId()));
            Mockito.verify(companyStore).findAll();
        });

    }

    @After
    public void teardown() {
        Helpers.stop(application);
    }
}