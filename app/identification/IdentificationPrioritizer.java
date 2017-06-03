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
        private Map<Long, Company> companies;

        public PriorityComparator(Map<Long, Company> companies) {
            this.companies = companies;
        }

        @Override
        public int compare(Identification i1, Identification i2) {
            if (companies.get(i1.getCompanyId()).overdue(i1))
                return 1;
            if (companies.get(i2.getCompanyId()).overdue(i2))
                return -1;
            if (i1.getCompanyId() == i2.getCompanyId())
                return -Long.compare(i1.getWaitingTime(), i2.getWaitingTime());
            else {
                Company c1 = companies.get(i1.getCompanyId());
                Company c2 = companies.get(i2.getCompanyId());
                int result = -(c1.getSlaPercentage().compareTo(c2.getSlaPercentage()));
                if (result != 0)
                    return result;
                result = Long.compare(c1.getSlaTime(), c2.getSlaTime());
                if (result != 0)
                    return result;
                return c1.getCurrentSla().compareTo(c2.getCurrentSla());
            }
        }
    }
}
