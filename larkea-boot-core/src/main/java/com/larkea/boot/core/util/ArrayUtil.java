package com.larkea.boot.core.util;

import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;

public class ArrayUtil extends ArrayUtils {

	private ArrayUtil() {
	}

	public static <T> T getFirst(@NonNull T[] elements, T defaultValue) {
		return elements.length == 0 ? defaultValue : elements[0];
	}

}
