package identification;

import java.util.*;

public class IdentificationPrioritizer {

    private Map<Long, Company> companies = new HashMap<>();
    private List<Identification> identifications = new ArrayList<>();

    public List<Identification> prioritize() {
        identifications.sort(new PriorityComparator());
        return identifications;
    }

    public void add(Identification identification) {
        identifications.add(identification);
    }

    public void add(Company company) {
        companies.put(company.getId(), company);
    }

    private static class PriorityComparator implements Comparator<Identification> {
        @Override
        public int compare(Identification i1, Identification i2) {
            return Long.compare(i2.waitingTime(), i1.waitingTime());
        }
    }
}
