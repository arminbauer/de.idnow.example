package utils.comparators;

import models.Identification;

import java.util.Comparator;

/**
 * Created by Wolfgang Ostermeier on 09.10.2017.
 *
 * Comparator class that compares Identification-Objects regarding their urgency
 */
public class IdentificationComparator implements Comparator<Identification> {


    /**
     * This compare-Methods prioritizes the Waiting-Time dependent on the SLA-Time before the Current SLA percentage
     * dependent on agreed SLA percentage. Only if the comparison of the Waiting-Time dependent on the SLA_time calculates
     * an equal priority for both Identification-Objects, then the comparison of the SLA-Time before the Current SLA
     * percentage is used to compare both Objects.
     * @param o1 the first Identification Object
     * @param o2 the second Identification Object
     * @return Integer that indicates which of the compared Objects is more urgent
     */
    @Override
    public int compare(Identification o1, Identification o2) {

        int compareSLAWaitingTime = Integer.compare(o1.getCompany().getSlaTime() - o1.getWaitingTime(),
                o2.getCompany().getSlaTime() - o2.getWaitingTime());
        if(compareSLAWaitingTime == 0){
            return Float.compare(o1.getCompany().getCurrentSlaPercentage()-o1.getCompany().getSlaPercentage(),
                    o2.getCompany().getCurrentSlaPercentage()-o2.getCompany().getSlaPercentage());
        }else return compareSLAWaitingTime;
    }
}
