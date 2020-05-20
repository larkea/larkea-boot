package com.larkea.boot.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.NumberUtils;

@UtilityClass
public class NumberUtil extends NumberUtils {

	/**
	 * 将 Object 转成 Integer
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @return 转后对象, 默认为 null
	 */
	public static Integer toInteger(Object object) {
		return toInteger(object, null);
	}

	/**
	 * 将 Object 转成 Integer
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @param defaultValue 如果转换失败，返回默认值
	 * @return 转后对象
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
	 * 将 Object 转成 Long
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @return 转后对象, 默认为 null
	 */
	public static Long toLong(Object object) {
		return toLong(object, null);
	}

	/**
	 * 将 Object 转成 Long
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @param defaultValue 如果转换失败，返回默认值
	 * @return 转后对象
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
	 * 将 Object 转成 Double
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @return 转后对象, 默认为 null
	 */
	public static Double toDouble(Object object) {
		return toDouble(object, null);
	}

	/**
	 * 将 Object 转成 Double
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @param defaultValue 如果转换失败，返回默认值
	 * @return 转后对象
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
	 * 将 Object 转成 Float
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @return 转后对象, 默认为 null
	 */
	public static Float toFloat(Object object) {
		return toFloat(object, null);
	}

	/**
	 * 将 Object 转成 Float
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @param defaultValue 如果转换失败，返回默认值
	 * @return 转后对象
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
	 * 将 Object 转成 Short
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @return 转后对象, 默认为 null
	 */
	public static Short toShort(Object object) {
		return toShort(object, null);
	}

	/**
	 * 将 Object 转成 Short
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @param defaultValue 如果转换失败，返回默认值
	 * @return 转后对象
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
	 * 将 Object 转成 Byte
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @return 转后对象, 默认为 null
	 */
	public static Byte toByte(Object object) {
		return toByte(object, null);
	}

	/**
	 * 将 Object 转成 Byte
	 *
	 * @param object Object 可以是 String 或者 Number 类型
	 * @param defaultValue 如果转换失败，返回默认值
	 * @return 转后对象
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
	 * 将 Object 转成 Boolean
	 *
	 * @param object Object 可以是 Boolean, String 或者 Number 类型
	 * @return 转后对象, 默认为 null
	 */
	public static Boolean toBoolean(Object object) {
		return toBoolean(object, null);
	}

	/**
	 * 将 Object 转成 Boolean
	 *
	 * @param object Object 可以是 Boolean, String 或者 Number 类型
	 * @param defaultValue 如果转换失败，返回默认值
	 * @return 转后对象
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
				return null;
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
