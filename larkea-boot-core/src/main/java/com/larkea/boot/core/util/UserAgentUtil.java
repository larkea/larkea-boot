package com.larkea.boot.core.util;


import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class UserAgentUtil {

	private final static String USER_AGENT = "User-Agent";

	private final static String X_USER_AGENT = "X-User-Agent";

	public static UserAgent getUserAgent(HttpServletRequest request) {
		String userAgentText = request.getHeader(USER_AGENT);
		if (StringUtils.isEmpty(userAgentText)) {
			userAgentText = request.getHeader(X_USER_AGENT);
		}

		UserAgent userAgent = UserAgent.parseUserAgentString(userAgentText);
		return userAgent;
	}

	public static String getUserAgentAsString(HttpServletRequest request) {
		UserAgent userAgent = getUserAgent(request);
		return userAgent.toString();
	}
}
