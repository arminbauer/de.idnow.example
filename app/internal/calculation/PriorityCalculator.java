package internal.calculation;

/**
 * Created by sebastian.walter on 02.02.2016.
 */
public class PriorityCalculator {

    private PrioFormula timeFormula;
    private PrioFormula slaPercentageFormula;

    public PriorityCalculator(){
        //Default formulas
        this.timeFormula = new TimeFormula();
        this.slaPercentageFormula = new SLApercentageFormula();
    }

    public void addTimeFormula(PrioFormula formula){
        this.timeFormula = formula;
    }

    public void addSLApercentageFormula(PrioFormula formula){
        this.slaPercentageFormula = formula;
    }

    /**
     * Calculates the overall priority for given parameters.
     * Note: Standard formulas will be used. You can set other by calling addTimeFormula() or addSLApercentageFormula().
     * @param slaTime
     * @param slaPercentage
     * @param currentSlaPercentage
     * @param waitingTime
     * @return a double value representing the priority (higher means higher priority)
     */
    public double calculate(int slaTime, float slaPercentage, float currentSlaPercentage, int waitingTime){
        double resultTime;
        double resultSLApercentage;
        resultTime = timeFormula.calculate(waitingTime / (double) slaTime);
        resultSLApercentage = slaPercentageFormula.calculate(currentSlaPercentage / slaPercentage);

        //you may want to add a factor to one or both of the values to set priority (or choose other prioFormulas)
        return resultTime + resultSLApercentage;
    }

}
