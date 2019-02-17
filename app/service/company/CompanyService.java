package service.company;

import api.Company;
import repository.CompanyRepository;
import service.ConversionService;

import javax.inject.Inject;

/**
 * Company service
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class CompanyService {
    private CompanyRepository companyRepository;
    private ConversionService conversionService;

    @Inject
    public CompanyService(CompanyRepository companyRepository, ConversionService conversionService) {
        this.companyRepository = companyRepository;
        this.conversionService = conversionService;
    }

    /**
     * Add new company if it doesn't exist in db
     *
     * @param company to add
     * @return <code>true</code> if the company was successfully added
     * <code>false</code> otherwise
     */
    public boolean addNewCompany(Company company) {
        return companyRepository.save(conversionService.convert(company));
    }

}
