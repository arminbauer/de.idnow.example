package service;

import com.google.inject.Inject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.test.Helpers;
import service.dto.CompanyDTO;
import service.ifaces.ICompanyService;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Created by sreenath on 16.07.16.
 */
public class CompanyServiceTest {

    public static Application app;
    ICompanyService companyService = new CompanyService();

    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    @Test
    public void createCompany() {
        CompanyDTO dto = new CompanyDTO();
        String id = UUID.randomUUID().toString();
        dto.setId(id);
        dto.setCurrentSlaPercent(0.9);
        dto.setName("TestCompany");
        dto.setSlaPercent(0.9);
        dto.setSlaTimeSeconds(80);
        companyService.create(dto);
        assertEquals(companyService.getById(id).getName(), "TestCompany");
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }
}
