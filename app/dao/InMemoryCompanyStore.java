package dao;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import models.Company;
import play.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nick on 13.05.16.
 */
@Singleton
public class InMemoryCompanyStore implements CompanyStore {

    private final Map<Long, Company> store = Maps.newConcurrentMap();
    private final AtomicLong sequence = new AtomicLong(1000);

    @Override
    public Company upsert(Company company) {
        if (company.getId() == null) {
            company.setId(sequence.getAndIncrement());
        }
        store.put(company.getId(), company);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Company saved. {}", company);
        }
        return company; // behaves like JPA 
    }

    @Override
    public Optional<Company> findById(Long companyId) {
        if (companyId == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(store.get(companyId));
        }
    }

    @Override
    public Collection<Company> findAll() {
        return store.values();
    }
}
