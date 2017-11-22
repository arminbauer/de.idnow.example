package models.company;

import com.google.inject.ImplementedBy;
import models.company.entities.Company;
import models.company.jsons.CompanyJson;
import models.store.IdNowStoreRepository;

@ImplementedBy(StoreCompanyRepository.class)
public interface CompanyRepository {
    CompanyJson addCompany(CompanyJson json) throws IdNowStoreRepository.DuplicatedElementException;

    Company getCompany(long id);
}
