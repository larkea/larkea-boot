package com.larkea.boot.core.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class SqlUtil {

	public static boolean toBoolean(int intBoolean) {
		return intBoolean == 1;
	}

	public static int toInt(boolean booleanValue) {
		return booleanValue ? 1 : 0;
	}

	/**
	 * 将 map 转为 bean，数据库中字段是下划线分隔的，转成驼峰式命名的bean
	 */
	@SneakyThrows
	public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
		T object = clazz.newInstance();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}

			String key = entry.getKey();

			List<String> snakeCaseFields = Lists.newArrayList(key.split("_")).stream()
					.map(StringUtils::capitalize).collect(
							Collectors.toList());
			String methodName = "set" + StringUtils.join(snakeCaseFields, "");
			Method set = clazz.getMethod(methodName,
					entry.getValue().getClass() == Timestamp.class ? Date.class
							: entry.getValue().getClass());
			set.invoke(object, entry.getValue());
		}

		return object;
	}

	public static String joinSql(String... items) {
		return String.join(" ", items);
	}

	public static String like(String keyword) {
		return new StringBuilder("%").append(keyword).append("%").toString();
	}
}
