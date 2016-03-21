package utils;

public class ValidationUtils {

	public static boolean isPositive(Number number) {
		return number != null && isGreaterThen(number, 0);
	}

	public static boolean isNegative(Number number) {
		return number != null && !isPositiveOrZero(number);
	}

	public static boolean isPositiveOrZero(Number number) {
		return number != null && isGreaterThen(number, -1);
	}

	public static boolean isGreaterThen(Number number, Number then) {
		if (number == null || then == null) {
			throw new IllegalArgumentException("Input parameters can't be null");
		}
		if (number instanceof Integer) {
			return number.intValue() > then.doubleValue();
		}

		if (number instanceof Long) {
			return number.longValue() > then.doubleValue();
		}

		if (number instanceof Double) {
			return number.doubleValue() > then.doubleValue();
		}
		throw new IllegalArgumentException("Provided number should is not an instance of Integer, Long or Double");
	}

}
