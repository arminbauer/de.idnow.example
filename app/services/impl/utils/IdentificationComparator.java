package services.impl.utils;

import persistence.CompanyEntity;
import persistence.IdentificationEntity;

import java.util.Comparator;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
public class IdentificationComparator implements
        Comparator<IdentificationEntity> {

    public IdentificationComparator() {
        super();
    }

    @Override
    public int compare(IdentificationEntity o1, IdentificationEntity o2) {
        if (o1.equals(o2)) {
            return 0;
        }

        CompanyEntity c1 = o1.getCompany();
        CompanyEntity c2 = o2.getCompany();

        Integer wt1 = o1.getWaitingTime();
        Integer wt2 = o2.getWaitingTime();
        int wtCmp = wt1.compareTo(wt2);

        if (c1.equals(c2)) {
            return wtCmp;
        }

        Integer st1 = c1.getSlaTime();
        Integer st2 = c2.getSlaTime();
        int stCmp = st2.compareTo(st1);

        Double csp1 = c1.getCurrentSlaPercentage();
        Double csp2 = c1.getCurrentSlaPercentage();
        int cspCmp = csp2.compareTo(csp1);

        if (cspCmp == 0) {
            if (wtCmp == 0) {
                return stCmp;
            } else {
                return wtCmp;
            }
        } else {
            return cspCmp;
        }

    }
}
