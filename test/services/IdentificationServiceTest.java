package services;

import org.junit.Before;
import org.junit.Test;
import repositories.CompanyRepository;
import repositories.IdentificationRepository;
import services.dto.IdentificationDTO;
import services.exceptions.InvalidCompanyException;
import services.impl.IdentificationServiceImpl;

import java.util.Date;

import static org.mockito.Mockito.mock;

/**
 * Created by ebajrami on 4/23/16.
 */
public class IdentificationServiceTest {

    private IdentificationService identificationService = null;

    @Before
    public void init() {
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        IdentificationRepository identificationRepository = mock(IdentificationRepository.class);
        identificationService = new IdentificationServiceImpl(companyRepository, identificationRepository);
    }

    @Test(expected = InvalidCompanyException.class)
    public void testIdentificationService() throws InvalidCompanyException {
        IdentificationDTO identificationDTO = new IdentificationDTO();
        identificationDTO.setId(1);
        identificationDTO.setName("Mocked Named");
        identificationDTO.setTime(new Date().getTime() / 1000);
        identificationDTO.setWaitingTime(45);
        identificationDTO.setCompanyId(1);
        identificationService.startIdentification(identificationDTO);

    }

}