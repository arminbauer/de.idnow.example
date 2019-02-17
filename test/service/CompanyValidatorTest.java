package service;

import api.Company;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Company validator test
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class CompanyValidatorTest extends WithApplication {
    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    private CompanyValidator companyValidator;

    @Before
    public void createCompanyValidator() {
        companyValidator = app.injector().instanceOf(CompanyValidator.class);
    }

    @Test
    public void testValidationWithoutIdAndName() {
        Company company = Company.builder()
                .slaTime(30L)
                .slaPercentage(90F).currentSlaPercentage(95F).build();
        List<String> validationErrors = companyValidator.validate(company);
        assertEquals("Company id is required! Company name is required!", String.join(" ", validationErrors));
    }
}
