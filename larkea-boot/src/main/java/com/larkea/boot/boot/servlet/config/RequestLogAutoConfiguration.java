package com.larkea.boot.boot.servlet.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.larkea.boot.boot.util.WebUtil;
import com.larkea.boot.core.util.StringPool;
import com.larkea.boot.core.util.StringUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(value = "larkea.boot.request.log", havingValue = "true")
@RequiredArgsConstructor
@Aspect
@Slf4j
public class RequestLogAutoConfiguration {

	private final ObjectMapper objectMapper;

	@Around(
			"execution(!static com.larkea.boot.core.result.Result *(..)) && " +
					"(@within(org.springframework.stereotype.Controller) || " +
					"@within(org.springframework.web.bind.annotation.RestController))"
	)
	public Object aroundRequest(ProceedingJoinPoint point) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) point.getSignature();
		Method method = methodSignature.getMethod();
		Object[] args = point.getArgs();

		List<String> bodyList = Lists.newArrayList();
		Map<String, String> formDataMap = Maps.newHashMap();
		Map<String, String> fileDataMap = Maps.newHashMap();


		for (int i = 0; i < args.length; i++) {
			Object value = args[i];
			MethodParameter methodParam = new MethodParameter(method, i);

			PathVariable pathVariable = methodParam.getParameterAnnotation(PathVariable.class);
			if (pathVariable != null) {
				continue;
			}

			// formData
			RequestParam requestParam = methodParam.getParameterAnnotation(RequestParam.class);
			if (requestParam != null) {
				String name = requestParam.value();
				if (value instanceof MultipartFile) {
					MultipartFile multipartFile = (MultipartFile) value;
					String fileName = multipartFile.getOriginalFilename();
					fileDataMap.put(name, fileName);
				}
				else {
					formDataMap.put(name, String.valueOf(value));
				}
				continue;
			}

			// Body
			RequestBody requestBody = methodParam.getParameterAnnotation(RequestBody.class);
			if (requestBody != null && value != null) {
				bodyList.add(toJson(value));
			}
		}
		HttpServletRequest request = WebUtil.getRequest();

		StringBuilder requestUrl = new StringBuilder()
				.append(request.getMethod())
				.append(StringPool.SPACE)
				.append(request.getRequestURI());
		// QueryString
		String queryString = request.getQueryString();
		if (StringUtil.isNotEmpty(queryString)) {
			requestUrl.append(StringPool.QUESTION_MARK).append(queryString);
		}

		// 构建成一条日志
		StringBuilder requestLog = new StringBuilder();

		List<Object> requestArgs = Lists.newArrayList();
		requestLog.append("==> {} ");
		requestArgs.add(requestUrl);

		// Header
		Enumeration<String> headerNames = request.getHeaderNames();
		String header = Collections.list(headerNames)
				.stream()
				.map(headerName -> String.format("-H %s=%s", headerName, request.getHeader(headerName)))
				.collect(Collectors.joining(StringPool.SPACE));
		requestLog.append("{} ");
		requestArgs.add(header);

		// formData
		if (!formDataMap.isEmpty()) {
			String formData = formDataMap.entrySet()
					.stream()
					.map(e -> String.format("-D %s=%s", e.getKey(), e.getValue()))
					.collect(Collectors.joining(StringPool.SPACE));
			requestLog.append("{} ");
			requestArgs.add(formData);
		}

		// fileData
		if (!fileDataMap.isEmpty()) {
			String fileData = fileDataMap.entrySet()
					.stream()
					.map(e -> String.format("-F %s=%s", e.getKey(), e.getValue()))
					.collect(Collectors.joining(StringPool.SPACE));
			requestLog.append("{} ");
			requestArgs.add(fileData);
		}

		// Body
		if (!bodyList.isEmpty()) {
			String body = String.join(StringPool.SPACE, bodyList);
			requestLog.append("Body:{} ");
			requestArgs.add(body);
		}

		// 执行时间开始
		long startNanoTime = System.nanoTime();
		LOGGER.info(requestLog.toString(), requestArgs.toArray());

		StringBuilder responseLog = new StringBuilder();
		List<Object> responseArgs = new ArrayList<>();
		responseLog.append("{} ");
		responseArgs.add(requestUrl);
		try {
			Object result = point.proceed();
			responseLog.append("Result:{} ");
			responseArgs.add(toJson(result));
			return result;
		}
		finally {
			long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanoTime);
			String responseLogWrapper = "<== " + millis + "ms " + responseLog;
			LOGGER.info(responseLogWrapper, responseArgs.toArray());
		}
	}

	private String toJson(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		}
		catch (Exception e) {
			LOGGER.error("Write value as json error", e);
			return "cannot be serialize to JSON";
		}
	}
}
