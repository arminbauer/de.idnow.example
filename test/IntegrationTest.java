import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import dao.CompanyStore;
import models.Company;
import models.Identification;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.test.Helpers;
import services.IdentificationsQueue;

import java.util.List;

import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationTest extends AbstractTest {

    @Mock
    private CompanyStore store;
    @Mock
    private IdentificationsQueue queue;

    @Before
    public void setUp() throws Exception {
        GuiceApplicationBuilder builder = new GuiceApplicationLoader().
                builder(new GuiceApplicationLoader.Context(Environment.simple())).bindings(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CompanyStore.class).toInstance(store);
                bind(IdentificationsQueue.class).toInstance(queue);
            }
        });
        Guice.createInjector(builder.applicationModule()).injectMembers(this);
        Helpers.start(application);

    }

    @Test
    public void should_display_companies_and_idents_tables() {
        Company company1 = RandomObjects.randomCompany();
        Company company2 = RandomObjects.randomCompany();
        Identification ident1 = RandomObjects.randomIdentification();
        Identification ident2 = RandomObjects.randomIdentification();
        Identification ident3 = RandomObjects.randomIdentification();
        Mockito.doReturn(Lists.newArrayList(ident1, ident2, ident3)).when(queue).getAllIdentifications();
        Mockito.doReturn(Lists.newArrayList(company1, company2)).when(store).findAll();

        running(testServer(port, application), HTMLUNIT, browser -> {
            browser.goTo(localUrl("/").getUrl());

            List<WebElement> companiesRows = browser.getDriver().findElements(By.cssSelector("#companies tbody tr"));
            assertThat(companiesRows.size(), Matchers.is(2));

            List<WebElement> identRows = browser.getDriver().findElements(By.cssSelector("#idents tbody tr"));
            assertThat(identRows.size(), Matchers.is(3));

            Mockito.verify(queue).getAllIdentifications();
            Mockito.verify(store).findAll();
        });
    }

}
