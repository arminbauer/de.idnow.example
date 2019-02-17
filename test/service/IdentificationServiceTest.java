package service;

import api.Company;
import api.Identification;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;
import service.company.CompanyService;
import service.identification.IdentificationsService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Identification service test
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationServiceTest extends WithApplication {

    private IdentificationsService identificationsService;
    private CompanyService companyService;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Before
    public void createCompanyService() {
        identificationsService = app.injector().instanceOf(IdentificationsService.class);
        companyService = app.injector().instanceOf(CompanyService.class);
    }

    @Test
    public void testSortedIdentifications() {

        Company company = Company.builder().id(1L).name("Company 1").slaTime(60L).slaPercentage(90F)
                .currentSlaPercentage(95F).build();
        Company company2 = Company.builder().id(2L).name("Company 2").slaTime(120L)
                .slaPercentage(80F).currentSlaPercentage(80F).build();
        Company company3 = Company.builder().id(3L).name("Company 3").slaTime(60L)
                .slaPercentage(90F).currentSlaPercentage(95F).build();
        Company company4 = Company.builder().id(4L).name("Company 4").slaTime(60L)
                .slaPercentage(90F).currentSlaPercentage(90F).build();

        companyService.addNewCompany(company);
        companyService.addNewCompany(company2);
        companyService.addNewCompany(company3);
        companyService.addNewCompany(company4);

        Identification identification = Identification.builder().id(1L).waitingTime(45L).companyId(1L).build();
        Identification identification2 = Identification.builder().id(2L).waitingTime(60L).companyId(2L).build();
        Identification identification3 = Identification.builder().id(3L).waitingTime(45L).companyId(2L).build();
        Identification identification4 = Identification.builder().id(4L).waitingTime(30L).companyId(3L).build();
        Identification identification5 = Identification.builder().id(5L).waitingTime(45L).companyId(4L).build();

        identificationsService.addNewIdentification(identification);
        identificationsService.addNewIdentification(identification2);
        identificationsService.addNewIdentification(identification3);
        identificationsService.addNewIdentification(identification4);
        identificationsService.addNewIdentification(identification5);

        assertEquals("2,3,5,1,4", getQueueString(identificationsService.getSortedIdentifications()));
    }

    private String getQueueString(List<Identification> identifications) {
        return identifications.stream().map(identification -> identification.getId().toString())
                .collect(Collectors.joining(","));
    }
}
