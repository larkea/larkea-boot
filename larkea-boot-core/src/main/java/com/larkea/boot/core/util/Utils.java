package com.larkea.boot.core.util;

import java.util.Date;

import org.springframework.beans.BeansException;

/**
 * Combine all utils together.
 */
public class Utils {

	private Utils() {
	}

	public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException {
		BeanUtil.copyPropertiesIgnoreNull(source, target);
	}

	public static Date now() {
		return DateUtil.now();
	}

	public static String randomString(int length) {
		return RandomUtil.randomString(length);
	}

	public static String uuid() {
		return IdUtil.uuid();
	}

	public static String simpleUuid() {
		return IdUtil.simpleUuid();
	}

	public static String base64Uuid() {
		return IdUtil.base62Uuid();
	}
}
