package services;

import models.Company;
import models.Identification;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Talal Ahmed on 18/08/2018
 */
public class IdentificationService {

    private Map<Integer, Company> companies; // can also be a repository
    private List<Identification> identifications;

    public IdentificationService() {
        companies = new HashMap<>();
        identifications = new ArrayList<>();
    }

    public Map<Integer, Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Map<Integer, Company> companies) {
        this.companies = companies;
    }

    public List<Identification> getIdentifications() {
        return identifications;
    }

    public void setIdentifications(List<Identification> identifications) {
        this.identifications = identifications;
    }

    public void addCompany(Company company) {
        this.companies.put(company.getId(), company);
    }

    public void addIdentification(Identification identification) {
        identifications.add(identification);
    }

    public SortedSet<Identification> priorityList() {
        // Defines the rules for prioritizing identifications
        TreeSet<Identification> sorted = identifications.stream()
                .collect(Collectors
                        .toCollection(() -> new TreeSet<>((o1, o2) -> {
                            if (o1.getWaitingTime() == o2.getWaitingTime()) {
                                if (o1.getCompanyId() == o2.getCompanyId()) return 0; // don't care

                                Company c1 = companies.get(o1.getCompanyId());
                                Company c2 = companies.get(o2.getCompanyId());

                                double diff1 = c1.getCurrentSLAPercentage() - c1.getSlaPercentage();
                                double diff2 = c2.getCurrentSLAPercentage() - c2.getSlaPercentage();

                                if (diff1 > diff2) return -1; // Behind required SLAs

                                if (c1.getSlaTime() > c2.getSlaTime()) return -1; // Urgent SLAs

                                if (diff1 < diff2) return 1; // SLA of one achieved - take the other
                            }

                            return Long.compare(o1.getWaitingTime(), o2.getWaitingTime()); // Prioritize on waiting time
                        })));

        return sorted.descendingSet();
    }
}
