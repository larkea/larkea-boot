package com.larkea.boot.boot.util;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

/**
 * Miscellaneous utilities for web applications.
 */
@Slf4j
@UtilityClass
public class WebUtil extends WebUtils {

	public static HttpServletRequest getRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
				.map(x -> (ServletRequestAttributes) x)
				.map(ServletRequestAttributes::getRequest)
				.orElse(null);
	}

}

