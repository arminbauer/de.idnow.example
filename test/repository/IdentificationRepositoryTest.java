package repository;

import models.CompanyEntity;
import models.IdentificationEntity;
import models.IdentificationStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;

/**
 * Identification repository test
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationRepositoryTest extends WithApplication {

    private CompanyRepository companyRepository;
    private IdentificationRepository identificationRepository;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Before
    public void createIdentificationRepository() {
        identificationRepository = app.injector().instanceOf(IdentificationRepository.class);
        companyRepository = app.injector().instanceOf(CompanyRepository.class);
    }

    @After
    public void cleanCompanyAndIdentifications() {
        identificationRepository.delete(1L);
        companyRepository.delete(1L);
    }

    @Test
    public void testCompanySave() {
        CompanyEntity companyEntity = CompanyEntity.builder()
                .id(2L).name("Company 1")
                .slaTime(30L)
                .slaPercentage(90F).currentSlaPercentage(95F).build();

        companyRepository.save(companyEntity);

        IdentificationEntity identification = IdentificationEntity.builder()
                .id(1L).name("Identification 1").waitingTime(30L).companyEntity(companyEntity)
                .identificationStatus(IdentificationStatus.NEW).build();

        identificationRepository.save(identification);
        IdentificationEntity identificationToCheck = identificationRepository.getById(1L);
        assertEquals("Ids are not the same", identification.getId(), identificationToCheck.getId());
        assertEquals("Names are not the same", identification.getName(), identificationToCheck.getName());
        assertEquals("Waiting times are not the same", identification.getWaitingTime(), identificationToCheck.getWaitingTime());
        assertEquals("Status is not the same", identification.getIdentificationStatus(), identificationToCheck.getIdentificationStatus());

        assertEquals("Company ids are not the same", identification.getCompanyEntity().getId(), identificationToCheck.getCompanyEntity().getId());
        assertEquals("Compane names are not the same", identification.getCompanyEntity().getName(), identificationToCheck.getCompanyEntity().getName());
        assertEquals("Company SLA times are not the same", identification.getCompanyEntity().getSlaTime(), identificationToCheck.getCompanyEntity().getSlaTime());
        assertEquals("Company SLA percentages are not the same", identification.getCompanyEntity().getSlaPercentage(), identificationToCheck.getCompanyEntity().getSlaPercentage());
        assertEquals("Company current SLA percentages are not the same", identification.getCompanyEntity().getCurrentSlaPercentage(), identificationToCheck.getCompanyEntity().getCurrentSlaPercentage());

    }
}
