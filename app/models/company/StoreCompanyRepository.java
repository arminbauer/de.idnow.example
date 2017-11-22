package models.company;

import models.company.entities.Company;
import models.company.factories.CompanyFactory;
import models.company.jsons.CompanyJson;
import models.store.IdNowStoreRepository;

import javax.inject.Inject;

public class StoreCompanyRepository implements CompanyRepository {
    private final IdNowStoreRepository idNowStoreRepository;

    @Inject
    public StoreCompanyRepository(IdNowStoreRepository idNowStoreRepository) {
        this.idNowStoreRepository = idNowStoreRepository;
    }

    @Override
    public CompanyJson addCompany(CompanyJson json) throws IdNowStoreRepository.DuplicatedElementException {
        idNowStoreRepository.addCompany(
                CompanyFactory.getInstance().fromCompanyJson(json)
        );
        return json;
    }

    @Override
    public Company getCompany(long id) {
        return idNowStoreRepository.getCompany(id);
    }
}
