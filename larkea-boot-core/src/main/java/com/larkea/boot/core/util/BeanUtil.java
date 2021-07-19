package com.larkea.boot.core.util;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

public class BeanUtil extends BeanUtils {

	private BeanUtil() {
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = Sets.newHashSet();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException {
		String[] ignoreProperties = getNullPropertyNames(source);
		copyProperties(source, target, ignoreProperties);
	}

	public static <S, T> List<T> copyList(List<S> sources, Supplier<T> targetSupplier,
			Function<S, T> converter) {
		return sources.stream().map(source -> {
			T target = targetSupplier.get();
			copyProperties(source, target);

			if (Optional.ofNullable(converter).isPresent()) {
				target = converter.apply(source);
			}

			return target;
		}).collect(Collectors.toList());
	}

	public static <S, T> List<T> copyList(List<S> sources, Supplier<T> targetSupplier) {
		return copyList(sources, targetSupplier, null);
	}

}
