package models;

import java.util.Comparator;
import java.util.Optional;

/**
 * Created by Florian Schmidt on 07.11.2017.
 */
public class SlaComparator implements Comparator<Identification> {

    private float calculatePriority(Identification identification) {
        if(identification == null) {
            return Float.MAX_VALUE;
        }

        Optional<Company> oCompany = CompanyStore.findCompany(identification.getCompanyId());

        if(!oCompany.isPresent()) {
            return Float.MAX_VALUE;
        }

        Company company = oCompany.get();
        return company.getCurrentSlaPercentage() / company.getSlaPercentage()
                * (company.getSlaTime() - identification.getWaitingTime());
    }

    @Override
    public int compare(Identification o1, Identification o2) {
        float prio1 = calculatePriority(o1);
        float prio2 = calculatePriority(o2);

        return Float.compare(prio1, prio2);
    }
}
