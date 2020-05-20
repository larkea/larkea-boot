package com.larkea.boot.core.util;


import lombok.experimental.UtilityClass;

/**
 * 判断登录名称类型.
 */
@UtilityClass
public class LoginNameUtil {

	public static LoginNameEnum decide(String loginName) {
		int digitalCount = 0;
		for (char c : loginName.toCharArray()) {
			if (c == '@') {
				return LoginNameEnum.EMAIL;
			}

			if (c >= '0' && c <= '9') {
				digitalCount++;
			}
		}

		if (digitalCount == loginName.length()) {
			return LoginNameEnum.MOBILE;
		}

		return LoginNameEnum.USERNAME;
	}

	public enum LoginNameEnum {
		MOBILE, EMAIL, USERNAME
	}
}
