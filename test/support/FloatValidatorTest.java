package support;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by nick on 14.05.16.
 */
@RunWith(Parameterized.class)
public class FloatValidatorTest {

    private Float minValue;
    private Float maxValue;
    private Float value;

    private boolean expectedResult;

    public FloatValidatorTest(Float minValue, Float value, Float maxValue, boolean expectedResult) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters(name = "{index}: {0} <= {1} <= {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1F, 2F, 3.0F, true},
                {0.5F, 0.4F, 1.0F, false},
                {0.5F, 0.5F, 1.0F, true},
                {0.5F, 0.9F, 0.9F, true},
                {0.5F, null, 0.9F, false},

        });
    }

    @Test
    public void testName() throws Exception {
        ValidFloat annotation = Mockito.mock(ValidFloat.class);
        Mockito.when(annotation.minValue()).thenReturn(minValue);
        Mockito.when(annotation.maxValue()).thenReturn(maxValue);

        FloatValidator floatValidator = new FloatValidator();
        floatValidator.initialize(annotation);

        Assert.assertThat(floatValidator.isValid(value, null), Matchers.is(expectedResult));


    }
}