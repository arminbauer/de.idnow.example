package dao;

import com.google.inject.ImplementedBy;
import models.Company;

import java.util.Collection;
import java.util.Optional;

@ImplementedBy(InMemoryCompanyStore.class)
public interface CompanyStore {

    Company upsert(Company company);

    Optional<Company> findById(Long companyId);

    default Company findByIdOrThrow(Long companyId) {
        Optional<Company> company = findById(companyId);
        if (company.isPresent()) {
            return company.get();
        } else {
            throw new EntityNotFountException(String.format("Company doesn't exist! CompanyId='%s'!", companyId));
        }
    }

    Collection<Company> findAll();
}
