package com.larkea.boot.core.util;


import lombok.experimental.UtilityClass;

/**
 * 判断登录名称类型.
 */
@UtilityClass
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
