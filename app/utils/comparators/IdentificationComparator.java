package utils.comparators;

import models.Identification;

import java.util.Comparator;

public class IdentificationComparator implements Comparator<Identification> {

    @Override
    public int compare(Identification o1, Identification o2) {

        int compareSLAWaitingTime = Integer.compare(o1.getCompany().getSlaTime() - o1.getWaitingTime(), o2.getCompany().getSlaTime() - o2.getWaitingTime());
        if(compareSLAWaitingTime == 0){
            return Float.compare(o1.getCompany().getCurrentSlaPercentage()-o1.getCompany().getSlaPercentage(),o2.getCompany().getCurrentSlaPercentage()-o2.getCompany().getSlaPercentage());
        }else return compareSLAWaitingTime;
    }
}
