package com.larkea.boot.core.util;

import org.springframework.util.NumberUtils;

public class NumberUtil extends NumberUtils {

	private NumberUtil() {
	}

	/**
	 * Convert Object to Integer
	 *
	 * @param object Object can be String or Number
	 * @return Integer or null
	 */
	public static Integer toInteger(Object object) {
		return toInteger(object, null);
	}

	/**
	 * Convert Object to Integer
	 *
	 * @param object Object can be String or Number
	 * @param defaultValue if convert failed, return defaultValue
	 * @return Integer or defaultValue
	 */
	public static Integer toInteger(Object object, Integer defaultValue) {
		if (object instanceof Number) {
			return ((Number) object).intValue();
		}
		if (object instanceof CharSequence) {
			String value = ((CharSequence) object).toString();
			try {
				return Integer.parseInt(value);
			}
			catch (NumberFormatException ignore) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * Convert Object to Long
	 *
	 * @param object Object can be String or Number
	 * @return Long or null
	 */
	public static Long toLong(Object object) {
		return toLong(object, null);
	}

	/**
	 * Convert Object to Long
	 *
	 * @param object Object can be String or Number
	 * @param defaultValue if convert failed, return defaultValue
	 * @return Long or defaultValue
	 */
	public static Long toLong(Object object, Long defaultValue) {
		if (object instanceof Number) {
			return ((Number) object).longValue();
		}
		if (object instanceof CharSequence) {
			String value = ((CharSequence) object).toString();
			try {
				return Long.parseLong(value);
			}
			catch (NumberFormatException ignore) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * Convert Object to Double
	 *
	 * @param object Object can be String or Number
	 * @return Double or null
	 */
	public static Double toDouble(Object object) {
		return toDouble(object, null);
	}

	/**
	 * Convert Object to Double
	 *
	 * @param object Object can be String or Number
	 * @param defaultValue if convert failed, return defaultValue
	 * @return Double or defaultValue
	 */
	public static Double toDouble(Object object, Double defaultValue) {
		if (object instanceof Number) {
			return ((Number) object).doubleValue();
		}
		if (object instanceof CharSequence) {
			String value = ((CharSequence) object).toString();
			try {
				return Double.parseDouble(value);
			}
			catch (NumberFormatException ignore) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * Convert Object to Float
	 *
	 * @param object Object can be String or Number
	 * @return Float or null
	 */
	public static Float toFloat(Object object) {
		return toFloat(object, null);
	}

	/**
	 * Convert Object to Float
	 *
	 * @param object Object can be String or Number
	 * @param defaultValue if convert failed, return defaultValue
	 * @return Float or defaultValue
	 */
	public static Float toFloat(Object object, Float defaultValue) {
		if (object instanceof Number) {
			return ((Number) object).floatValue();
		}
		if (object instanceof CharSequence) {
			String value = ((CharSequence) object).toString();
			try {
				return Float.parseFloat(value);
			}
			catch (NumberFormatException ignore) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * Convert Object to Short
	 *
	 * @param object Object can be String or Number
	 * @return Short or null
	 */
	public static Short toShort(Object object) {
		return toShort(object, null);
	}

	/**
	 * Convert Object to Short
	 *
	 * @param object Object can be String or Number
	 * @param defaultValue if convert failed, return defaultValue
	 * @return Short or defaultValue
	 */
	public static Short toShort(Object object, Short defaultValue) {
		if (object instanceof Number) {
			return ((Number) object).shortValue();
		}
		if (object instanceof CharSequence) {
			String value = ((CharSequence) object).toString();
			try {
				return Short.parseShort(value);
			}
			catch (NumberFormatException ignore) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * Convert Object to Byte
	 *
	 * @param object Object can be String or Number
	 * @return Byte or null
	 */
	public static Byte toByte(Object object) {
		return toByte(object, null);
	}

	/**
	 * Convert Object to Byte
	 *
	 * @param object Object can be String or Number
	 * @param defaultValue if convert failed, return defaultValue
	 * @return Byte or defaultValue
	 */
	public static Byte toByte(Object object, Byte defaultValue) {
		if (object instanceof Number) {
			return ((Number) object).byteValue();
		}
		if (object instanceof CharSequence) {
			String value = ((CharSequence) object).toString();
			try {
				return Byte.parseByte(value);
			}
			catch (NumberFormatException ignore) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * Convert Object to Boolean
	 *
	 * @param object Object can be String or Number
	 * @return Boolean or null
	 */
	public static Boolean toBoolean(Object object) {
		return toBoolean(object, null);
	}

	/**
	 * Convert Object to Boolean
	 *
	 * @param object Object can be String or Number
	 * @param defaultValue if convert failed, return defaultValue
	 * @return Boolean or defaultValue
	 */
	public static Boolean toBoolean(Object object, Boolean defaultValue) {
		if (object instanceof Boolean) {
			return (Boolean) object;
		}

		if (object instanceof Number) {
			try {
				Long value = convertNumberToTargetClass((Number) object, Long.class);
				return value != 0;
			}
			catch (IllegalArgumentException ignore) {
				return defaultValue;
			}
		}

		if (object instanceof CharSequence) {
			String value = ((CharSequence) object).toString();
			try {
				return Boolean.parseBoolean(value);
			}
			catch (NumberFormatException ignore) {
				return defaultValue;
			}
		}
		return defaultValue;
	}
}
