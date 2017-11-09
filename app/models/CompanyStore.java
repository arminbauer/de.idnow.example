package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Florian Schmidt on 07.11.2017.
 */
public class CompanyStore {
    private static boolean isInitialized = false;

    public static void initialize() throws DataStoreException {
        if(!isInitialized) {
            Company company1 = new Company();
            company1.setId(UUID.fromString("c18705a8-41fe-46b8-ba01-78b760103603"));
            company1.setName("Company1");
            company1.setSlaTime(120);
            company1.setSlaPercentage(0.9f);
            company1.setCurrentSlaPercentage(0.9f);

            Company company2 = new Company();
            company2.setId(UUID.fromString("59d00c3b-f4b7-44b7-a005-aea18a396f72"));
            company2.setName("Company2");
            company2.setSlaTime(120);
            company2.setSlaPercentage(0.9f);
            company2.setCurrentSlaPercentage(0.9f);

            try {
                CompanyStore.add(company1);
                CompanyStore.add(company2);
            } catch(DataStoreException ex) {
                throw new DataStoreException("CompanyStore initialization failed.");
            }
            isInitialized = true;
        }
    }

    private static List<Company> companies = new ArrayList<>();

    public static boolean exists(UUID id) {
        return companies.stream().anyMatch(existingCompany -> existingCompany.getId().equals(id));
    }

    public static void add(Company company) throws DataStoreException {
        if(exists(company.getId())) {
            throw new DataStoreException(
                    String.format("Cannot add company, because a company with id '%s' already exists",
                                  company.getId()));
        }
        companies.add(company);
    }

    private static List<Company> getCompanies() {
        return Collections.unmodifiableList(companies);
    }

    public static Optional<Company> findCompany(UUID id) {
        return companies.stream()
                        .filter(company -> company.getId().equals(id))
                        .findFirst();
    }

    public static Map<String, String> getCompanyDetails() {
        return getCompanies().stream().collect(
                Collectors.toMap(company -> company.getId().toString(), Company::getName));
    }

    public static void clear() {
        companies.clear();
    }
}
