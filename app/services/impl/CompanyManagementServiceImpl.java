package services.impl;

import com.google.inject.Inject;
import repositories.CompanyRepository;
import repositories.models.Company;
import services.CompanyManagementService;
import services.dto.CompanyDTO;
import services.exceptions.InvalidCompanyException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service used for manupulation of company data. For now only adding company implmeneted as reqested
 * Created by ebajrami on 4/23/16.
 */
public class CompanyManagementServiceImpl implements CompanyManagementService {

    private CompanyRepository companyRepository;

    @Inject
    public CompanyManagementServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void addCompany(CompanyDTO companyDTO) throws InvalidCompanyException {
        checkNotNull(companyDTO, "Company cannot be null");
        checkArgument(companyDTO.getCurrentSlaPercentage() >= 0, "Current SLA percentage cannot be less then 0%");
        checkArgument(companyDTO.getSlaPercentage() >= 0, "Current SLA percentage cannot be less then 0%");
        checkArgument(companyDTO.getSlaTime() >= 0, "SLA time cannot be less then 0");

        // check if company with id already exists?
        if (companyRepository.findById(companyDTO.getId()) != null) {
            throw new InvalidCompanyException("Company with specified ID already exists");
        }
        Company company = new Company();
        company.setCurrentSlaPercentage(companyDTO.getCurrentSlaPercentage());
        company.setId(company.getId());
        company.setName(companyDTO.getName());
        company.setSlaPercentage(companyDTO.getSlaPercentage());
        company.setSlaTime(companyDTO.getSlaTime());
        companyRepository.add(company);
    }

}
