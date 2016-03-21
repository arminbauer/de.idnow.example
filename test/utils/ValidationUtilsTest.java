package utils;

import static com.google.common.truth.Truth.assertThat;
import static utils.ValidationUtils.isPositive;

import org.junit.Test;

public class ValidationUtilsTest {

	@Test
	public void testIsLongZeroPositive() {
		assertThat(isPositive(0L)).isFalse();
	}

	@Test
	public void testIsIntegerZeroPositive() {
		assertThat(isPositive(0)).isFalse();
	}

	@Test
	public void testIsDoubleZeroPositive() {
		assertThat(isPositive(0.0)).isFalse();
	}

	@Test
	public void testIsLongOnePositive() {
		assertThat(isPositive(1L)).isTrue();
	}

	@Test
	public void testIsIntegerOnePositive() {
		assertThat(isPositive(1)).isTrue();
	}

	@Test
	public void testIsDoubleOnePositive() {
		assertThat(isPositive(1.0)).isTrue();
	}

	@Test
	public void testIsLongZeroNegative() {
		assertThat(ValidationUtils.isNegative(0L)).isFalse();
	}

	@Test
	public void testIsIntegerZeroNegative() {
		assertThat(ValidationUtils.isNegative(0)).isFalse();
	}

	@Test
	public void testIsDoubleZeroNegative() {
		assertThat(ValidationUtils.isNegative(0.0)).isFalse();
	}

	@Test
	public void testIsLongOneNegative() {
		assertThat(ValidationUtils.isNegative(1L)).isFalse();
	}

	@Test
	public void testIsIntegerOneNegative() {
		assertThat(ValidationUtils.isNegative(1)).isFalse();
	}

	@Test
	public void testIsDoubleOneNegative() {
		assertThat(ValidationUtils.isNegative(1.0)).isFalse();
	}

	@Test
	public void testIsLongZeroPositiveOrZero() {
		assertThat(ValidationUtils.isPositiveOrZero(0L)).isTrue();
	}

	@Test
	public void testIsIntegerZeroPositiveOrZero() {
		assertThat(ValidationUtils.isPositiveOrZero(0)).isTrue();
	}

	@Test
	public void testIsDoubleZeroPositiveOrZero() {
		assertThat(ValidationUtils.isPositiveOrZero(0L)).isTrue();
	}

	@Test
	public void test8GreaterThen7() {
		assertThat(ValidationUtils.isGreaterThen(8, 7)).isTrue();
	}

	@Test
	public void test7NotGreaterThen10() {
		assertThat(ValidationUtils.isGreaterThen(7.1, 10)).isFalse();
	}

	@Test
	public void testSameValues() {
		assertThat(ValidationUtils.isGreaterThen(8.1, 8.1)).isFalse();
	}

	@Test
	public void testIsPositiveWithNull() {
		assertThat(ValidationUtils.isPositive(null)).isFalse();
	}

	@Test
	public void testIsNegativeWithNull() {
		assertThat(ValidationUtils.isNegative(null)).isFalse();
	}

	@Test
	public void testIsPositiveOrZeroWithNull() {
		assertThat(ValidationUtils.isPositiveOrZero(null)).isFalse();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsGreaterFirstNull() {
		assertThat(ValidationUtils.isGreaterThen(null, 8.1)).isFalse();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsGreaterSecondNull() {
		assertThat(ValidationUtils.isGreaterThen(8, null)).isFalse();
	}

	@Test(expected = IllegalArgumentException.class)
	public void notSupportedNumberType() {
		isPositive(8f);
	}

}