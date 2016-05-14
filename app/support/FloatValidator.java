package support;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by nick on 14.05.16.
 */
public class FloatValidator implements ConstraintValidator<ValidFloat, Float> {

    private float minValue;
    private float maxValue;

    @Override
    public void initialize(ValidFloat annotation) {
        this.minValue = annotation.minValue();
        this.maxValue = annotation.maxValue();
    }

    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        return value != null && minValue <= value && value <= maxValue;
    }

}
