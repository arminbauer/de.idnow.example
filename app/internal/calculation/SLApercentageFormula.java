package internal.calculation;

/**
 * Created by sebastian.walter on 02.02.2016.
 */
public class SLApercentageFormula implements PrioFormula {

    @Override
    public double calculate(double input) {
        return 1 / input; //input should never be 0
    }
}
