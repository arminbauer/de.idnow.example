package controllers.core;

import models.Company;
import models.Identification;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAppStorage implements AppStorage {

    private final static InMemoryAppStorage INSTANCE = new InMemoryAppStorage();

    public static InMemoryAppStorage getInstance() {
        return INSTANCE;
    }

    private final List<Identification> identifications = Collections.synchronizedList(new ArrayList<Identification>());
    private final Map<Long, Company> companies = new ConcurrentHashMap<>();

    @Override
    public void addCompany(Company company) throws DuplicatedEntityException {
        if (companies.containsKey(company.getId())) {
            throw new DuplicatedEntityException(company.getId(), "Company already added");
        }
        companies.put(company.getId(), company);
    }

    @Override
    public Company getCompany(long id) {
        return companies.get(id);
    }

    @Override
    public void startIdentification(Identification ident) throws EntityNotFoundException {
        Company company = companies.get(ident.getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException(ident.getCompanyId());
        }

        // VC: Bind ident to company for better read performance
        ident.setCompany(company);

        identifications.add(ident);

        // VC: For better read performance it would be better to sort the list here after adding new items
        // identifications.sort((o1, o2) -> o1.compareTo(o2));
        // VC: But requirements state to do it in the controller
    }

    @Override
    public Collection<Identification> getIdentifications() {
        return identifications;
    }

    public void clear() {
        identifications.clear();
        companies.clear();
    }
}
