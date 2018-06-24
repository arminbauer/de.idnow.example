package services;

import comparators.PendingIdentificationComparator;
import models.Company;
import models.Identification;
import repositories.GenericRepository;
import repositories.IdentificationRepository;

import javax.inject.Inject;
import java.util.List;

public class IdentificationService {
    @Inject
    private IdentificationRepository identificationRepository;
    @Inject
    private GenericRepository<Company> companyRepository;

    public void save(Identification identification, Integer companyId) {
        Company company = companyRepository.findById(Company.class, companyId);

        identification.setCompany(company);
        identification.setPending(true);
        identificationRepository.save(identification);
    }

    public List<Identification> getAllPending() {
        return identificationRepository.listAllPendingOrdered();
//        SECOND SOLUTION
//        List<Identification> result = identificationRepository.listAllPending();
//        result.sort(new PendingIdentificationComparator());
//
//        return result;
    }
}
