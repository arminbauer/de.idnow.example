package service;

import com.avaje.ebean.Ebean;
import models.Company;
import models.Identification;
import repository.IdentificationRepository;

import javax.inject.Inject;
import java.util.List;

public class IdentificationServiceImpl implements IdentificationService {

    @Inject
    private IdentificationRepository identificationRepository;

    @Override
    public List<Identification> getIdentificationsOrderBySLA() {
        return identificationRepository.getIdentificationsOrderBySLA();
    }

    @Override
    public void save(Identification identification) {
        checkCompanyId(identification.companyId);
        identificationRepository.save(identification);
    }

    private void checkCompanyId(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Identification companyId should be defined.");
        }
        Company company = Ebean.find(Company.class, companyId);
        if (company == null) {
            throw new IllegalArgumentException("Company with id " + companyId + " is not found.");
        }
    }
}
