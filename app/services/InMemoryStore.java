package services;

import models.Company;
import models.Identification;

import javax.inject.Singleton;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InMemoryStore {
    private Map<UUID, Company> companyMap = new ConcurrentHashMap<>();
    private Map<UUID, Identification> identificationMap = new ConcurrentHashMap<>();
    private Map<UUID, Map<UUID, Identification>> identificationsByCompanyId = new ConcurrentHashMap<>();

    public Map<UUID, Company> getCompanyMap() {
        return companyMap;
    }

    public Map<UUID, Identification> getIdentificationMap() {
        return identificationMap;
    }

    public Map<UUID, Map<UUID, Identification>> getIdentificationsByCompanyId() {
        return identificationsByCompanyId;
    }

    public void addCompany(Company company) {
        companyMap.put(company.getId(), company);
    }

    public void addIdentification(Identification identification) {
        identificationMap.put(identification.getId(), identification);

        identificationsByCompanyId
                .computeIfAbsent(identification.getCompanyId(), (a) -> new ConcurrentHashMap<>())
                .put(identification.getId(), identification);
    }
}
