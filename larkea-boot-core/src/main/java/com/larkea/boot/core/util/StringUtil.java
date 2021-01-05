package com.larkea.boot.core.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {
    private StringUtil() {
    }

    public static boolean timeConstantEquals(String s1, String s2) {
		if (s1 == s2) {
			return true;
		}

		if (s1 == null || s2 == null) {
			return false;
		}

		if (s1.length() != s2.length()) {
			return false;
		}

		int result = 0;

		// time-constant comparison
		for (int i = 0; i < s1.length(); i++) {
			result |= s1.charAt(i) ^ s2.charAt(i);
		}

		return result == 0;
	}
}
