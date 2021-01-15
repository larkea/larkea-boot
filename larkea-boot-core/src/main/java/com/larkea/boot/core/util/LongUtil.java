package com.larkea.boot.core.util;

public class LongUtil {

	private final static char[] DIGITS = {
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
	};

	private final static long BASE = 62;

	private LongUtil() {
	}

	public static String toBase62(long number) {
		if (number < 0) {
			throw new IllegalArgumentException("number must not be negative");
		}

		StringBuilder result = new StringBuilder();

		while (number > 0) {
			long quotient = number / BASE;
			long remainder = number - quotient * BASE;

			result.insert(0, DIGITS[(int) (remainder)]);
			number = quotient;
		}

		return (result.length() == 0) ? String.valueOf(DIGITS[0]) : result.toString();
	}

	public static long fromBase62(String base62String) {
		long value = 0;
		int chValue;
		for (int i = 0; i < base62String.length(); i++) {
			chValue = indexOf(base62String.charAt(i));
			value += (long) (chValue * Math.pow(BASE, base62String.length() - i - 1));
		}
		return value;
	}

	private static int indexOf(char ch) {
		for (int i = 0; i < DIGITS.length; i++) {
			if (ch == DIGITS[i]) {
				return i;
			}
		}
		return -1;
	}
}
