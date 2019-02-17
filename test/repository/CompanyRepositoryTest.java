package repository;

import models.CompanyEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

/**
 * Company repository test cases
 * In intellij is working only with Ebean 4.x enhancement plugin (for me at least)
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class CompanyRepositoryTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    private CompanyRepository companyRepository;

    @Before
    public void createCompanyRepository() {
        companyRepository = app.injector().instanceOf(CompanyRepository.class);
    }

    @After
    public void cleanCompany() {
        companyRepository.delete(1L);
    }

    @Test
    public void testCompanySave() {
        CompanyEntity companyEntity = CompanyEntity.builder()
                .id(1L).name("Company 1")
                .slaTime(30L)
                .slaPercentage(90F).currentSlaPercentage(95F).build();

        companyRepository.save(companyEntity);
        CompanyEntity companyEntityToCheck = companyRepository.getById(companyEntity.getId());
        Assert.assertEquals("Ids are not the same", companyEntity.getId(), companyEntityToCheck.getId());
        Assert.assertEquals("Names are not the same", companyEntity.getName(), companyEntityToCheck.getName());
        Assert.assertEquals("SLA times are not the same", companyEntity.getSlaTime(), companyEntityToCheck.getSlaTime());
        Assert.assertEquals("SLA percentages are not the same", companyEntity.getSlaPercentage(), companyEntityToCheck.getSlaPercentage());
        Assert.assertEquals("Current SLA percentages are not the same", companyEntity.getCurrentSlaPercentage(), companyEntityToCheck.getCurrentSlaPercentage());
    }
}
