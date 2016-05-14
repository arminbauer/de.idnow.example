import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import dao.CompanyStore;
import models.Identification;
import org.hamcrest.Matchers;
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
import services.IdentificationsQueue;
import services.IdentificationsQueueDuplicateException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
@RunWith(MockitoJUnitRunner.class)
public class IdentificationsControllerTest extends AbstractTest {
    
    @Mock
    private IdentificationsQueue identsQueue;

    @Mock
    private CompanyStore companyStore;

    @Before
    public void setUp() throws Exception {
        GuiceApplicationBuilder builder = new GuiceApplicationLoader().
                builder(new GuiceApplicationLoader.Context(Environment.simple())).bindings(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CompanyStore.class).toInstance(companyStore);
                bind(IdentificationsQueue.class).toInstance(identsQueue);
            }
        });
        Guice.createInjector(builder.applicationModule()).injectMembers(this);
        Helpers.start(application);

    }

    @Test
    public void should_add_new_ident_to_queue() {
        running(testServer(port, application), () -> {
            Identification identToPost = RandomObjects.randomIdentification();

            WSResponse response = localUrl("/api/v1/identifications").setContentType("application/json").post(Json.toJson(identToPost)).get(10_000);
            assertThat(response.getStatus(), is(NO_CONTENT));
            ArgumentCaptor<Identification> captor = ArgumentCaptor.forClass(Identification.class);
            try {
                Mockito.verify(identsQueue).add(captor.capture());
                Identification passedToQueue = captor.getValue();
                Assert.assertThat(passedToQueue.getId(), Matchers.is(identToPost.getId()));
                Assert.assertThat(passedToQueue.getName(), Matchers.is(identToPost.getName()));
                Assert.assertThat(passedToQueue.getCreatedTs(), Matchers.is(identToPost.getCreatedTs()));
                Assert.assertThat(passedToQueue.getCompanyId(), Matchers.is(identToPost.getCompanyId()));
                Assert.assertThat(passedToQueue.getWaitTimeSeconds(), Matchers.is(identToPost.getWaitTimeSeconds()));
            } catch (IdentificationsQueueDuplicateException e) {
                fail(e.getMessage());
            }
        });
    }

    @Test
    public void should_not_add_ident_to_queue_when_validation_fails() {
        running(testServer(port, application), () -> {
            WSResponse response = localUrl("/api/v1/identifications").setContentType("application/json").post(Json.toJson(new Identification())).get(10_000);
            assertThat(response.getStatus(), is(BAD_REQUEST));
            JsonNode body = response.asJson();
            Assert.assertThat(body.get("errorType").asText(), is("validationFailed"));
            Assert.assertThat(body.get("details").isArray(), is(true));
            Assert.assertThat(body.get("details").size(), is(1));
            Mockito.verifyZeroInteractions(companyStore);
        });
    }

    @Test
    public void should_not_add_new_ident_to_queue_when_validation_fails() {
        running(testServer(port, application), () -> {
            WSResponse response = localUrl("/api/v1/identifications").setContentType("application/json").post(Json.toJson(new Identification())).get(10_000);
            assertThat(response.getStatus(), is(BAD_REQUEST));
            Mockito.verifyZeroInteractions(identsQueue);
        });
    }

    @Test
    public void should_return_conflict_when_duplicate_exception() {
        running(testServer(port, application), () -> {
            try {
                Mockito.doThrow(IdentificationsQueueDuplicateException.class).when(identsQueue).add(Mockito.any(Identification.class));
                WSResponse response = localUrl("/api/v1/identifications").setContentType("application/json").post(Json.toJson(RandomObjects.randomIdentification())).get(10_000);
                assertEquals(response.getStatus(), CONFLICT);
                Mockito.verify(identsQueue).add(Mockito.any(Identification.class));
            } catch (IdentificationsQueueDuplicateException e) {
                fail(e.getMessage());
            }
        });

    }

    @Test
    public void should_return_all_idents_in_queue() {
        running(testServer(port, application), () -> {
            Identification ident1 = RandomObjects.randomIdentification();
            Identification ident2 = RandomObjects.randomIdentification();
            Identification ident3 = RandomObjects.randomIdentification();
            Mockito.doReturn(Lists.newArrayList(ident1, ident2, ident3)).when(identsQueue).getAllIdentifications();

            WSResponse response = localUrl("/api/v1/identifications").get().get(10_000);
            assertEquals(response.getStatus(), OK);
            JsonNode jsonNode = response.asJson();
            assertEquals(jsonNode.isArray(), true);
            assertThat(jsonNode.size(), is(3));
            assertThat(jsonNode.get(0).findValue("id").asLong(), is(ident1.getId()));
            assertThat(jsonNode.get(1).findValue("id").asLong(), is(ident2.getId()));
            assertThat(jsonNode.get(2).findValue("id").asLong(), is(ident3.getId()));

            Mockito.verify(identsQueue).getAllIdentifications();
        });

    }


}
