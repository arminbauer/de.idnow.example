package service.identification;

import models.IdentificationEntity;

import java.util.Comparator;

/**
 * Implements the logic of identifications sorting
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationComparator implements Comparator<IdentificationEntity> {

    private float slaDifferenceWeight;
    private float slaWeight;
    private float waitingTimeWeight;

    /**
     * The order of the identifications regarding the SLAs of the companies (some identifications can be not finished)
     */
    public IdentificationComparator() {
        this.slaDifferenceWeight = 1;
        this.slaWeight = 0;
        this.waitingTimeWeight = 0;

    }

    /**
     * In case if we want to provide custom weights
     *
     * @param slaDifferenceWeight weight for sla difference ratio ratio calculation
     * @param slaWeight           weight for sla percentage ratio weight calculation
     * @param waitingTimeWeight   weight for time difference ratio weight calculation
     */
    public IdentificationComparator(float slaDifferenceWeight, float slaWeight, float waitingTimeWeight) {
        this.slaDifferenceWeight = slaDifferenceWeight;
        this.slaWeight = slaWeight;
        this.waitingTimeWeight = waitingTimeWeight;
    }

    /**
     * Compares two identification:
     * 1. If companies have the same slaTime, slaPercentage
     * the identification which has greater waiting time difference will be the first
     * 2. Then compares based on total weight (sum of sla difference ratio weight, sla percentage ratio weight and time difference ratio weight)
     *
     * @param identification1 first identification to check
     * @param identification2 second identification to check
     * @return the order of sorting
     */
    @Override
    public int compare(IdentificationEntity identification1, IdentificationEntity identification2) {

        long waitingTime1 = identification1.getWaitingTime();
        long waitingTime2 = identification2.getWaitingTime();

        long slaTime1 = identification1.getCompanyEntity().getSlaTime();
        long slaTime2 = identification2.getCompanyEntity().getSlaTime();

        float slaPercentage1 = identification1.getCompanyEntity().getSlaPercentage();
        float slaPercentage2 = identification2.getCompanyEntity().getSlaPercentage();

        float currentSlaPercentage1 = identification1.getCompanyEntity().getCurrentSlaPercentage();
        float currentSlaPercentage2 = identification2.getCompanyEntity().getCurrentSlaPercentage();


        if (slaPercentage1 == slaPercentage2) {
            if (currentSlaPercentage1 == currentSlaPercentage2) {
                return Long.compare(waitingTime2 - slaTime2, waitingTime1 - slaTime1); // reverse order
            }
        }

        return Float.compare(getTotalWeight(currentSlaPercentage1, slaPercentage1, slaTime1, waitingTime1),
                getTotalWeight(currentSlaPercentage2, slaPercentage2, slaTime2, waitingTime2));
    }


    /**
     * Calculates total weight,  it's the sum of
     * 1. sla percentage difference (current sla percentage - sla percentage) ratio weight
     * 2. sla percentage ratio weight
     * 3. time difference (sla time - waiting time) ratio weight
     *
     * @param currentSlaPercentage - the SLA (Service Level Agreement) percentage of the company
     * @param slaPercentage        - the current SLA percentage of this company in this month
     * @param slaTime              - the SLA (Service Level Agreement) time of this company in seconds
     * @param waitingTime          - the current waiting time of the identification in seconds (since the user started)
     * @return total weight
     */
    private float getTotalWeight(float currentSlaPercentage, float slaPercentage, long slaTime, long waitingTime) {
        return getSlaDifferenceRatioWeight(currentSlaPercentage, slaPercentage)
                + getSlaPercentageRatioWeight(slaPercentage) + getTimeDifferenceRatioWeight(slaTime, waitingTime);


    }

    /**
     * Calculates the weight of SLA percentage difference (currentSlaPercentage - slaPercentage) ratio
     *
     * @param currentSlaPercentage - the current SLA percentage of this company in this month
     * @param slaPercentage        - the SLA (Service Level Agreement) percentage of the company
     * @return the weight of Sla difference ratio
     */
    private float getSlaDifferenceRatioWeight(float currentSlaPercentage, float slaPercentage) {
        return slaDifferenceWeight * (currentSlaPercentage - slaPercentage) / slaPercentage;
    }

    /**
     * Calculates sla percentage ratio weight (in case if we want to increase priority level of identifications with higher sla percentage)
     *
     * @return sla percentage ratio weight
     */
    private float getSlaPercentageRatioWeight(float slaPercentage) {
        return slaWeight * slaPercentage / 100;
    }


    /**
     * Calculates the ratio weight between sla time and waiting time
     *
     * @param slaTime     - the SLA (Service Level Agreement) time of this company in seconds
     * @param waitingTime - the current waiting time of the identification in seconds (since the user started)
     * @return time difference ratio weight
     */
    private float getTimeDifferenceRatioWeight(long slaTime, long waitingTime) {
        return waitingTimeWeight * (slaTime - waitingTime) / slaTime;
    }

    public void setSlaDifferenceWeight(float slaDifferenceWeight) {
        this.slaDifferenceWeight = slaDifferenceWeight;
    }

    public void setSlaWeight(float slaWeight) {
        this.slaWeight = slaWeight;
    }

    public void setWaitingTimeWeight(float waitingTimeWeight) {
        this.waitingTimeWeight = waitingTimeWeight;
    }
}
