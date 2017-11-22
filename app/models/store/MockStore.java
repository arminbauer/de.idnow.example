package models.store;

import models.company.entities.Company;
import models.identification.entities.Identification;

import java.util.*;

public class MockStore implements IdNowStoreRepository {
    private final static Set<Company> companyStore;
    private final static Set<Identification> identificationStore;
    static {
        companyStore = new HashSet<>();
        identificationStore = new HashSet<>();
    }

   /* @Override
    public Collection<Company> getCompanyStore() {
        return companyStore;
    }*/

    @Override
    public List<Identification> getIdentificationList() {
        List<Identification> cloneList = new ArrayList<>();
        cloneList.addAll(identificationStore);
        return cloneList;
    }

    @Override
    public Company addCompany(Company company) throws DuplicatedElementException {
        if (companyStore.add(company)) {
            return company;
        }
        throw new DuplicatedElementException();
    }

    static class ResultWrapper {
        Company company;
    }

    @Override
    public Company getCompany(long id) {
        final ResultWrapper result = new ResultWrapper();

        companyStore.forEach(company -> {
            if (company.getId() == id) result.company = company;
        });

        return result.company;
    }

    @Override
    public Identification addIdentification(Identification identification) throws DuplicatedElementException, CompanyNotExist {
        if (getCompany(identification.getCompanyId())== null) {
            throw new CompanyNotExist();
        }
        if (identificationStore.add(identification)) {
            return identification;
        }

        throw new DuplicatedElementException();
    }

    @Override
    public Identification getIdentificaation(long id) {
        return null;
    }


}
