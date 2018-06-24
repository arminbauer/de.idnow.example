package service;

import models.Company;
import models.Identification;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import repositories.GenericRepository;
import services.IdentificationService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;

public class IdentificationServiceTest {

    private static IdentificationService service;

    private static Company company;
    private static FakeApplication app;

    @BeforeClass
    public static void beforeAll() {
        app = fakeApplication();

        start(app);

        service = app.injector().instanceOf(IdentificationService.class);

        createCompany();
    }

    @AfterClass
    public static void stopAll() {
        stop(app);
    }

    @Test
    public void newIdentificationCreatedAsPending() {
        final Identification identification = new Identification();

        identification.setName("Johannes");
        identification.setTime(1529838253L);
        identification.setWaitingTime(10);

        service.save(identification, company.getId());

        assertNotNull(identification.getId());
        assertTrue(identification.isPending());
    }

    private static void createCompany() {
        GenericRepository<Company> companyRepository = app.injector().instanceOf(GenericRepository.class);

        company = new Company();
        company.setName("Company A");
        company.setSlaTime(60);
        company.setSlaPercentage(.9d);
        company.setCurrentSlaPercentage(.95d);

        companyRepository.save(company);
    }
}
