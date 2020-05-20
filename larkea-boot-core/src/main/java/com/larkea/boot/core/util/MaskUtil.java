package com.larkea.boot.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MaskUtil {

	public static String maskMobile(String mobile) {
		if (mobile == null || mobile.length() < 11) {
			return mobile;
		}

		return new StringBuilder(mobile.substring(0, 3))
				.append("****")
				.append(mobile.substring(7))
				.toString();
	}
}
