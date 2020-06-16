package com.larkea.boot.boot.servlet.error;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;
import com.larkea.boot.core.exception.SystemException;
import com.larkea.boot.core.result.Result;
import com.larkea.boot.core.result.SystemResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

/**
 * Lark-Boot Default implementation of {@link ErrorAttributes}. Provides the following attributes
 * when possible:
 * <ul>
 * <li>code - The error code</li>
 * <li>message - The error message</li>
 * <li>success - The state of api call</li>
 * <li>meta - The debug meta object</li>
 * <ul>
 *  <li>status - The status code</li>
 *  <li>error - The error reason</li>
 *  <li>exception - The class name of the root exception (if configured)</li>
 *  <li>timestamp - The time that the errors were extracted</li>
 *  <li>errors - Any {@link ObjectError}s from a {@link BindingResult} exception
 *  <li>trace - The exception stack trace</li>
 *  <li>path - The URL path when the exception was raised</li>
 * </ul>
 * </ul>
 *
 * @author Wang Le
 * @see ErrorAttributes
 * @since 1.0.0
 */
@Slf4j
public class LarkeaBootErrorAttributes extends DefaultErrorAttributes {

    public LarkeaBootErrorAttributes() {
        this(false);
    }

    public LarkeaBootErrorAttributes(boolean includeException) {
        super();
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        String path = (String) errorAttributes.get("path");
        Integer status = (Integer) errorAttributes.get("status");
        String reason = (String) errorAttributes.get("error");
        Throwable error = getError(webRequest);

        Result<?> result;
        if (error == null) {
            LOGGER.error("path={},status={},reason={}", path, status, reason);
            result = Result.failed(SystemResultCode.FAILED);
        } else if (error instanceof SystemException) {
            LOGGER.error("path={},status={},reason={}", path, status, reason, error);
            result = Optional.ofNullable(((SystemException) error).getResult())
                    .orElse(Result.failed(SystemResultCode.FAILED));
        } else {
            LOGGER.error("path={},status={},reason={}", path, status, reason, error);
            result = Result.failed(SystemResultCode.FAILED);
        }

        Map<String, Object> meta = Maps.newLinkedHashMap();
        if (options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE)) {
            meta.put("trace", errorAttributes.get("trace"));
        }

        if (options.isIncluded(ErrorAttributeOptions.Include.EXCEPTION)) {
            meta.put("exception", errorAttributes.get("exception"));
        }

        if (!meta.isEmpty()) {
            result.setMeta(meta);
        }

        return toMap(result);
    }

    private Map<String, Object> toMap(Result<?> result) {
        return toMap(result, null);
    }

    private Map<String, Object> toMap(Result<?> result, Object meta) {
        if (result == null) {
            return null;
        }

        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("success", result.isSuccess());
        map.put("code", result.getCode());
        map.put("message", result.getMessage());

        if (meta != null) {
            map.put("meta", meta);
        }
        return map;
    }
}
