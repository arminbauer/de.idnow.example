package internal.calculation;

/**
 * Created by sebastian.walter on 02.02.2016.
 */
public class TimeFormula implements PrioFormula {
    @Override
    public double calculate(double input) {
        double result = Math.pow(10, (input-0.75));
        return result;
    }
}
