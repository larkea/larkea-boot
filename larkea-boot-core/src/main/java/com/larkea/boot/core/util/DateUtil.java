package com.larkea.boot.core.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_DATE = "yyyy-MM-dd";

	public static final String PATTERN_TIME = "HH:mm:ss";

	/**
	 * Java 8 data time formatter
	 */
	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter
			.ofPattern(DateUtil.PATTERN_DATETIME).withZone(ZoneId.systemDefault());

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
			.ofPattern(DateUtil.PATTERN_DATE).withZone(ZoneId.systemDefault());

	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
			.ofPattern(DateUtil.PATTERN_TIME).withZone(ZoneId.systemDefault());

	private DateUtil() {
	}

	public static Date now() {
		return new Date();
	}

	public static String formatDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return format(date, PATTERN_DATETIME);
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		return format(date, PATTERN_DATE);
	}

	public static String formatTime(Date date) {
		if (date == null) {
			return null;
		}
		return format(date, PATTERN_TIME);
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDateTime = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		return localDateTime.format(formatter);
	}

	public static Date parseDateTime(String date) {
		if (date == null) {
			return null;
		}
		return parse(date, PATTERN_DATETIME);
	}

	public static Date parseDate(String date) {
		if (date == null) {
			return null;
		}
		return parse(date, PATTERN_DATE);
	}

	public static Date parseTime(String date) {
		if (date == null) {
			return null;
		}
		return parse(date, PATTERN_TIME);
	}

	public static Date parse(String dateString, String pattern) {
		if (StringUtil.isBlank(dateString)) {
			return null;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
