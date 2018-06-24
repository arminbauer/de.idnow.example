package service;

import models.Company;
import models.Identification;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import repositories.GenericRepository;
import repositories.IdentificationRepository;
import services.IdentificationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;

public class IdentificationServiceTest {

    private static IdentificationService service;
    private static IdentificationRepository identificationRepository;

    private static FakeApplication app;

    @BeforeClass
    public static void beforeAll() {
        app = fakeApplication();

        start(app);

        service = app.injector().instanceOf(IdentificationService.class);
        identificationRepository = app.injector().instanceOf(IdentificationRepository.class);
    }

    @AfterClass
    public static void stopAll() {
        stop(app);
    }

    @After
    public void after() {
        identificationRepository.deleteAll();
    }

    @Test
    public void newIdentificationCreatedAsPending() {
        final Company company = createCompany("A", 60, 0.9, 0.9);

        final Identification identification = createIdentification("Johannes", 1529838253L, 10, company.getId());

        assertNotNull(identification.getId());
        assertTrue(identification.isPending());
    }

    @Test
    public void whenTheIdentificationIsDecidedByWaitingTime() {
        final Company company = createCompany("A", 60, 0.9, 0.9);

        final Identification identificationA = createIdentification("Johannes", 1529838253L, 30, company.getId());
        final Identification identificationB = createIdentification("Pipa", 1529838253L, 45, company.getId());

        final List<Identification> result = service.getAllPending();

        assertEquals("Pipa should come first since it is waiting more", Arrays.asList(identificationB, identificationA), result);
    }

    @Test
    public void whenSlaTimeIsTheSameButCurrentSlaIsDifferent() {
        final Company companyA = createCompany("A", 60, 0.9, 0.95);
        final Company companyB = createCompany("B", 60, 0.9, 0.90);

        final Identification identificationA = createIdentification("Johannes", 1529838253L, 30, companyA.getId());
        final Identification identificationB = createIdentification("Pipa", 1529838253L, 30, companyB.getId());

        final List<Identification> result = service.getAllPending();

        assertEquals("Pipa should come first since company B has a worse current SLA", Arrays.asList(identificationB, identificationA), result);
    }

    @Test
    public void whenSlaTimeIsMoreUrgent() {
        final Company companyA = createCompany("A", 60, 0.9, 0.95);
        final Company companyB = createCompany("B", 120, 0.8, 0.95);

        final Identification identificationA = createIdentification("Johannes", 1529838253L, 30, companyA.getId());
        final Identification identificationB = createIdentification("Pipa", 1529838253L, 30, companyB.getId());

        final List<Identification> result = service.getAllPending();

        assertEquals("Johannes should come first since company A more urgent SLA", Arrays.asList(identificationA, identificationB), result);
    }

    @Test
    public void whenSlaTimeIsMoreUrgentWithWaitingTime() {
        final Company companyA = createCompany("A", 60, .9, .95);
        final Company companyB = createCompany("B", 120, .8, .80);

        final Identification identificationA = createIdentification("Johannes", 1529838253L, 45, companyA.getId());
        final Identification identificationB = createIdentification("Pipa", 1529838254L, 30, companyB.getId());

        final List<Identification> result = service.getAllPending();

        assertEquals("Johannes should come first since company A more urgent SLA", Arrays.asList(identificationA, identificationB), result);
    }

    private Company createCompany(String name, int slaTime, double slaPercentage, double currentSla) {
        GenericRepository<Company> companyRepository = app.injector().instanceOf(GenericRepository.class);

        Company company = new Company();
        company.setName(name);
        company.setSlaTime(slaTime);
        company.setSlaPercentage(slaPercentage);
        company.setCurrentSlaPercentage(currentSla);

        companyRepository.save(company);

        return company;
    }

    private Identification createIdentification(String clientName, long time, int waitingTime, int companyId) {
        final Identification identification = new Identification();
        identification.setName(clientName);
        identification.setTime(time);
        identification.setWaitingTime(waitingTime);

        service.save(identification, companyId);

        return identification;
    }
}
