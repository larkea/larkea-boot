package com.larkea.boot.core.util;


/**
 * Decide the type of login name in [EMAIL, MOBILE, USERNAME].
 */
public class LoginNameUtil {

	public static LoginNameType decide(String loginName) {
		int digitalCount = 0;
		for (char c : loginName.toCharArray()) {
			if (c == '@') {
				return LoginNameType.EMAIL;
			}

			if (c >= '0' && c <= '9') {
				digitalCount++;
			}
		}

		if (digitalCount == loginName.length()) {
			return LoginNameType.MOBILE;
		}

		return LoginNameType.USERNAME;
	}

	public enum LoginNameType {
		MOBILE, EMAIL, USERNAME
	}
}
