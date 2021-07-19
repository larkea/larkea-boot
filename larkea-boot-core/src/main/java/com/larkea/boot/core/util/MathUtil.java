package com.larkea.boot.core.util;

public class MathUtil {

	private MathUtil() {
	}

	/**
	 * value is compared to max/min.
	 * if value is grater than max, return max
	 * if value is less than min，return min
	 * otherwise return value
	 *
	 * @param value value to be compared
	 * @param max   max
	 * @param min   min
	 * @return expected value
	 */
	public static int maxmin(int value, int max, int min) {
		return value < min ? min : Math.min(value, max);
	}
}
