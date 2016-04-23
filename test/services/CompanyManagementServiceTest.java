package services;

import junit.framework.TestCase;
import org.junit.Test;
import repositories.CompanyRepository;
import repositories.models.Company;
import services.dto.CompanyDTO;
import services.exceptions.InvalidCompanyException;
import services.impl.CompanyManagementServiceImpl;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ebajrami on 4/23/16.
 */
public class CompanyManagementServiceTest extends TestCase {

    private CompanyManagementService companyManagementService;

    @Test(expected = InvalidCompanyException.class)
    public void testAddCompanyTwice() throws InvalidCompanyException {
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        companyManagementService = new CompanyManagementServiceImpl(companyRepository);

        when(companyRepository.findById(1)).thenReturn(new Company());
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(1);
        companyDTO.setName("Mock company");
        companyDTO.setSlaTime(new Date().getTime());
        companyDTO.setSlaPercentage(0.9);
        companyDTO.setCurrentSlaPercentage(0.95);
        companyManagementService.addCompany(companyDTO);

    }
}