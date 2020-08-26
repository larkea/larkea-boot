package com.larkea.boot.core.util;

import java.util.Collection;

import lombok.NonNull;
import org.springframework.util.CollectionUtils;

public class CollectionUtil extends CollectionUtils {

    private CollectionUtil() {
    }

	public static <T> T getFirst(@NonNull Collection<T> elements, T defaultValue) {
		return elements.stream().findFirst().orElse(defaultValue);
	}
}
