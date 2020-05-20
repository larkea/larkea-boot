package com.larkea.boot.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtil {

	/**
	 * value 和 max/min 进行比较，大于 max 则为 max，小于 min 则为 min，否则为 value
	 *
	 * @param value 待比较值
	 * @param max   最大值
	 * @param min   最小值
	 * @return 符合条件的值
	 */
	public static int maxmin(int value, int max, int min) {
		return value < min ? min : value > max ? max : value;
	}
}
