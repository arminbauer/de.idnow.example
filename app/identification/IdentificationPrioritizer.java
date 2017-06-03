package identification;

import java.util.*;

public class IdentificationPrioritizer {

    private Map<Long, Company> companies = new HashMap<>();
    private List<Identification> identifications = new ArrayList<>();

    public List<Identification> prioritize() {
        identifications.sort(new PriorityComparator(companies));
        return identifications;
    }

    public void add(Identification identification) {
        identifications.add(identification);
    }

    public void add(Company company) {
        companies.put(company.getId(), company);
    }

    private static class PriorityComparator implements Comparator<Identification> {
        private static final Comparator<Company> countriesPriorityComparator = Comparator
                .comparing(Company::getSlaPercentage).reversed()
                .thenComparingLong(Company::getSlaTime)
                .thenComparing(Company::getCurrentSla);

        private Map<Long, Company> companies;

        PriorityComparator(Map<Long, Company> companies) {
            this.companies = companies;
        }

        @Override
        public int compare(Identification i1, Identification i2) {
            Company c1 = companies.get(i1.getCompanyId());
            Company c2 = companies.get(i2.getCompanyId());
            if (c1.overdue(i1))
                return 1;
            if (c2.overdue(i2))
                return -1;
            if (i1.getCompanyId() == i2.getCompanyId())
                return -Long.compare(i1.getWaitingTime(), i2.getWaitingTime());
            else {
                return countriesPriorityComparator.compare(c1, c2);
            }
        }
    }
}
