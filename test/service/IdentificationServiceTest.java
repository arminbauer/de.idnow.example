package service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;
import service.dto.CompanyDTO;
import service.dto.IdentificationDTO;
import service.ifaces.ICompanyService;
import service.ifaces.IIdentificationService;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by sreenath on 16.07.16.
 */
public class IdentificationServiceTest {

    public static Application app;
    ICompanyService companyService = new CompanyService();
    IIdentificationService identificationService = new IdentificationService();
    private static String companyId = "";

    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    @Before
    public void createCompany() {
        CompanyDTO dto = new CompanyDTO();
        companyId = UUID.randomUUID().toString();
        dto.setId(companyId);
        dto.setCurrentSlaPercent(0.9);
        dto.setName("TestCompany");
        dto.setSlaPercent(0.9);
        dto.setSlaTimeSeconds(80);
        companyService.create(dto);
        assertEquals(companyService.getById(companyId).getName(), "TestCompany");
    }

    @Test
    public void createIdentification() {
        // Create the first DTO object
        IdentificationDTO dto1 = new IdentificationDTO();
        String id1 = UUID.randomUUID().toString();
        dto1.setId(id1);
        dto1.setCompanyId(companyId);
        dto1.setNameOfUser("Test User1");
        dto1.setStartTime(1468670686);
        dto1.setWaitTimeSeconds(40);
        identificationService.create(dto1);

        // Create the second DTO object
        IdentificationDTO dto2 = new IdentificationDTO();
        String id2 = UUID.randomUUID().toString();
        dto2.setId(id2);
        dto2.setCompanyId(companyId);
        dto2.setNameOfUser("Test User2");
        dto2.setStartTime(1468670686);
        dto2.setWaitTimeSeconds(60);
        identificationService.create(dto2);

        // Retrieve the dtos and check their order
        List<IdentificationDTO> dtos = identificationService.getAll();
        assertEquals(dtos.get(0).getId(), id2);
        assertEquals(dtos.get(1).getId(), id1);
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }
}
