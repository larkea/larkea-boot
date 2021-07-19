package com.larkea.boot.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class IpUtil {
	public static final String X_FORWARD_FOR = "X-Forward-For";

	private IpUtil() {
	}

	public static String getClientIp(HttpServletRequest request) {
		String ipString = getClientIp(request.getHeader(X_FORWARD_FOR));
		if (StringUtils.isBlank(ipString)) {
			ipString = request.getRemoteAddr();
		}

		return ipString;
	}


	public static String getClientIp(String xForwardedFor) {
		if (StringUtils.isBlank(xForwardedFor)) {
			return "";
		}

		//X-Forwarded-For clientIp, proxyIp, proxyIp, ...
		String[] ips = xForwardedFor.split(",");
		if (ips.length > 0) {
			return ips[0];
		}

		return "";
	}

	public String[] getProxyIp(String xForwardedFor) {
		if (StringUtils.isBlank(xForwardedFor)) {
			return new String[] {};
		}

		//X-Forwarded-For clientIp, proxyIp, proxyIp, ...
		String[] ips = xForwardedFor.split(",");
		if (ips.length > 1) {
			String[] _proxyIps = new String[ips.length - 1];
			System.arraycopy(ips, 1, _proxyIps, 0, ips.length - 1);
			return _proxyIps;
		}

		return new String[] {};
	}
}
