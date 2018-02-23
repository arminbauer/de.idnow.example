package services;

import models.Company;
import models.Identification;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class IdentificationService {


    /*
     * I am not 100% sure that I deduced the proper ordering strategy for companies.
     *
     * Example 3, as written in README, gives impression that SLA_time has priority
     * over SLA_percentage and Current_SLA_percentage
     *
     * But looking at "example 4" gives impression that SLA_time should have a lower priority than
     * SLA percentage delta.
     *
     * Current implementation - we first consider the deltas for SLA percentages, then the SLA_time.

     * Open question is what should be done in case Current_SLA_percentage is less than SLA_percentage?
     * */
    private static final Comparator<Company> slaPercentageComparator =
            (o1, o2) -> Float.compare(
                    o1.getCurrentSLAPercentage() - o1.getSlaPercentage(),
                    o2.getCurrentSLAPercentage() - o2.getSlaPercentage()
            );
    private static final Comparator<Company> companyComparator =
            slaPercentageComparator.thenComparing(Comparator.comparingLong(Company::getSlaTime));

    @Inject
    private InMemoryStore store;

    public List<Identification> prioritizeAll() {

        Collection<Company> companies = store.getCompanyMap().values();

        Map<UUID, Map<UUID, Identification>> identificationsByCompanyId = store.getIdentificationsByCompanyId();

        List<Company> prioritizedCompanies = prioritizeCompanies(companies);

        return prioritizedCompanies
                .stream()
                .map(Company::getId)
                .map(companyId -> identificationsByCompanyId.get(companyId).values())
                .map(this::prioritizeWithinCompany)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Identification> prioritizeWithinCompany(Collection<Identification> identifications) {
        return identifications
                .parallelStream()
                .sorted(Comparator.comparingLong(Identification::getWaitingTime).reversed())
                .collect(Collectors.toList());
    }

    public List<Company> prioritizeCompanies(Collection<Company> companies) {
        return companies
                .parallelStream()
                .sorted(companyComparator)
                .collect(Collectors.toList());
    }
}
