package service;

import api.Company;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import static org.junit.Assert.assertTrue;

/**
 * Company service test
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class CompanyServiceTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    private CompanyService companyService;

    @Before
    public void createCompanyService() {
        companyService = app.injector().instanceOf(CompanyService.class);
    }

    @Test
    public void testAddNewCompany() {
        Company company = Company.builder()
                .id(1L).name("Company 1")
                .slaTime(30L)
                .slaPercentage(90F).currentSlaPercentage(95F).build();
        assertTrue(companyService.addNewCompany(company));
    }
}
