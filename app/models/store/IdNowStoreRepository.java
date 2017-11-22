package models.store;

import com.google.inject.ImplementedBy;
import models.company.entities.Company;
import models.identification.entities.Identification;

import java.util.Collection;
import java.util.List;

@ImplementedBy(MockStore.class)
public interface IdNowStoreRepository {
    //Collection<Company> getCompanyStore();
    List<Identification> getIdentificationList();

    Company addCompany(Company company) throws DuplicatedElementException;
    Company getCompany(long id);

    Identification addIdentification(Identification identification) throws DuplicatedElementException, CompanyNotExist;
    Identification getIdentificaation(long id);

    void deleteAll();

    class DuplicatedElementException extends Exception {
        public DuplicatedElementException() {
            super("The element has been added.");
        }
    }

    class CompanyNotExist extends Exception {
        public CompanyNotExist() {
            super("Could not found the company in store.");
        }
    }
}
