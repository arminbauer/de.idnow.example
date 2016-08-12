package engine;

import com.google.inject.Singleton;
import entities.Company;
import entities.Identification;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by vld on 9/08/16.
 */
@Singleton
public class IdentificationSystem {
    private final Map<String, Company> companies = new ConcurrentHashMap<>();
    private final NavigableSet<Identification> identifications = new ConcurrentSkipListSet<>(
            Comparator.<Identification>comparingDouble(i -> i.getCompany().getSlaPersentage() - i.getCompany().getCurrentSlaPersentage())
                    .thenComparingDouble(i -> i.getCompany().getCurrentSlaPersentage())
                    .thenComparingInt(i -> i.getWaitingTime()).reversed()
                    .thenComparingInt(i -> i.getCompany().getSlaTime())
                    .thenComparing(Identification::getId));

    public void queueIdentificaton(Identification identification) {
        identifications.add(identification);
    }

    public List<Identification> getPendingIdentifications() {
        // TODO: investigate effect of cuncurrent modification during this protection copy making, can incorrect order be achieved in the copy?
        return new ArrayList(identifications);
    }

    public void registerCompany(Company company) {
        companies.putIfAbsent(company.getId(), company);
    }

    public Optional<Company> getCompany(String id) {
        return Optional.ofNullable(companies.get(id));
    }

    public void clear() {
        companies.clear();
        identifications.clear();
    }
}