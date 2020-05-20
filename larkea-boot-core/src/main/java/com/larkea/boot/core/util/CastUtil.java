package com.larkea.boot.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CastUtil {

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object object) {
		return (T) object;
	}
}
