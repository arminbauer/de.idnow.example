package services;

import models.Company;
import models.Identification;
import repositories.GenericRepository;

import javax.inject.Inject;

public class IdentificationService {
    @Inject
    private GenericRepository<Identification> identificationRepository;
    @Inject
    private GenericRepository<Company> companyRepository;

    public void save(Identification identification, Integer companyId) {
        Company company = companyRepository.findById(Company.class, companyId);

        identification.setCompany(company);
        identification.setPending(true);
        identificationRepository.save(identification);
    }
}
