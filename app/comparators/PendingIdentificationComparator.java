package comparators;

import models.Company;
import models.Identification;

import java.util.Comparator;

public class PendingIdentificationComparator implements Comparator<Identification> {

    @Override
    public int compare(Identification identification1, Identification identification2) {
        Company company1 = identification1.getCompany();
        Company company2 = identification2.getCompany();

        if (company1.getSlaTime() > company2.getSlaTime()) {
            return 1;
        }
        if (company1.getSlaTime() < company2.getSlaTime()) {
            return -1;
        }

        if (company1.getCurrentSlaPercentage() > company2.getCurrentSlaPercentage()) {
            return 1;
        }
        if (company1.getCurrentSlaPercentage() < company2.getCurrentSlaPercentage()) {
            return -1;
        }

        if (identification1.getWaitingTime() < identification2.getWaitingTime()) {
            return 1;
        }
        if (identification1.getWaitingTime() > identification2.getWaitingTime()) {
            return -1;
        }

        return 0;
    }
}
