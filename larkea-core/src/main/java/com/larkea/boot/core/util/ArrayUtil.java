package com.larkea.boot.core.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;

@UtilityClass
public class ArrayUtil extends ArrayUtils {

	public static <T> T getFirst(@NonNull T[] elements, T defaultValue) {
		return elements.length == 0 ? defaultValue : elements[0];
	}

}
