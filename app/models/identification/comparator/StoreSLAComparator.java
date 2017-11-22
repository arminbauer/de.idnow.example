package models.identification.comparator;

import models.company.CompanyRepository;
import models.company.entities.Company;
import models.identification.entities.Identification;

import javax.inject.Inject;
import java.util.Objects;

public class StoreSLAComparator implements SLAComparator {
    private final CompanyRepository companyRepository;

    @Inject
    public StoreSLAComparator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    private float calPriority(Identification identification) {
        Company company = companyRepository.getCompany(identification.getCompanyId());
        if (company == null) throw new NullPointerException();

        return company.getCurrentSlaPercentage() / company.getSlaPercentage()
                * (company.getSlaTime() - identification.getWaitingTime());

    }


    @Override
    public int compare(Identification o1, Identification o2) {
        if (o1 == null || o2 == null) throw new NullPointerException();
        return Float.compare(calPriority(o1),calPriority(o2));
    }
}
