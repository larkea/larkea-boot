package com.larkea.boot.core.util;

import java.util.Date;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeansException;

@UtilityClass
public class Utils {

	public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException {
		BeanUtil.copyPropertiesIgnoreNull(source, target);
	}

	public static Date now() {
		return DateUtil.now();
	}

	public static String randomString(int length) {
		return RandomUtil.randomString(length);
	}

	/**
	 * 生成标准的UUID字符串
	 *
	 * @return UUID 字符串，带有减号分隔
	 */
	public static String uuid() {
		return IdUtil.uuid();
	}

	/**
	 * 生成不带减号分隔的UUID字符串
	 *
	 * @return 不带减号分隔的UUID字符串
	 */
	public static String simpleUuid() {
		return IdUtil.simpleUuid();
	}

	/**
	 * 生成 base62 的 uuid 字符串
	 *
	 * @return base62 string
	 */
	public static String base64Uuid() {
		return IdUtil.base62Uuid();
	}
}
