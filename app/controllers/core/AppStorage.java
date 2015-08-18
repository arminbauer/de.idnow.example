package controllers.core;

import models.Company;
import models.Identification;

import java.util.Collection;

public interface AppStorage {

    void addCompany(Company company) throws DuplicatedEntityException;

    Company getCompany(long id);

    void startIdentification(Identification ident) throws EntityNotFoundException;

    Collection<Identification> getIdentifications();
}
