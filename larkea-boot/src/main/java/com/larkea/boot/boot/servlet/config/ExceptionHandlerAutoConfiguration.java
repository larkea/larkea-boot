package com.larkea.boot.boot.servlet.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.larkea.boot.core.exception.SystemException;
import com.larkea.boot.core.result.Result;
import com.larkea.boot.core.result.SystemResultCode;
import com.larkea.boot.core.util.StringUtil;
import com.larkea.boot.core.validator.ValidationResult;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAutoConfiguration extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException ex,
			WebRequest request) {
		List<ValidationResult> validationResultList = Lists.newArrayList();
		ex.getConstraintViolations().forEach(cv -> {
			validationResultList.add(new ValidationResult(
					cv.getRootBeanClass().getSimpleName(),
					((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
					cv.getInvalidValue(),
					cv.getMessage()
			));
		});

		Result<Object> result = Result.failed(SystemResultCode.PARAM_VALID_ERROR);

		Map<String, Object> meta = Maps.newLinkedHashMap();
		handleBindingResult(meta, validationResultList);
		if (!meta.isEmpty()) {
			result.setMeta(meta);
		}

		return handleExceptionInternal(ex, result, BAD_REQUEST, request);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
				ex.getName(), ex.getValue(), Optional.ofNullable(ex.getRequiredType()).map(Class::getSimpleName).orElse("-"));
		Result<Object> result = Result.failed(SystemResultCode.PARAM_TYPE_ERROR, message);

		return handleExceptionInternal(ex, result, BAD_REQUEST, request);
	}

	@ExceptionHandler(SystemException.class)
	public ResponseEntity<Object> handleError(SystemException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getResult(), BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
	    String message = String.format("%s, Supported methods are [%s]", ex.getMessage(),
                StringUtil.join(ex.getSupportedMethods()));
		Result<?> result = Result.failed(SystemResultCode.METHOD_NOT_SUPPORTED, message);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
        List<MediaType> supportedMediaTypes = ex.getSupportedMediaTypes();
        String message = String.format("%s, Supported media types are [%s]", ex.getMessage(),
                StringUtil.join(supportedMediaTypes));
		Result<?> result = Result.failed(SystemResultCode.MEDIA_TYPE_NOT_SUPPORTED, message);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
			HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.MEDIA_TYPE_NOT_ACCEPTABLE);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
			HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		String message = String.format("Path variable '%s' is missing", ex.getVariableName());
		Result<?> result = Result.failed(SystemResultCode.PARAM_MISSING, message);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		String message = String.format("Request parameter '%s' is missing", ex.getParameterName());
		Result<?> result = Result.failed(SystemResultCode.PARAM_MISSING, message);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(
			ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.PARAM_BINDING_ERROR);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.PARAM_TYPE_ERROR);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.PARAM_TYPE_ERROR);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.MSG_NOT_READABLE);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.MSG_NOT_WRITABLE);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		Map<String, Object> meta = Maps.newLinkedHashMap();
		handleBindingResult(meta, ex.getBindingResult());

		Result<Object> result = Result.failed(SystemResultCode.PARAM_VALID_ERROR);

		if (!meta.isEmpty()) {
			result.setMeta(meta);
		}

		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(
			MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.PARAM_MISSING);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.PARAM_BINDING_ERROR);
		Map<String, Object> meta = Maps.newLinkedHashMap();
		handleBindingResult(meta, ex.getBindingResult());
		if (!meta.isEmpty()) {
			result.setMeta(meta);
		}
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.NOT_FOUND);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
			AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		Result<?> result = Result.failed(SystemResultCode.REQ_TIMEOUT);
		return this.handleExceptionInternal(ex, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		if (body instanceof Result) {
			return new ResponseEntity<>(body, status);
		}

		return new ResponseEntity<>(Result.failed(SystemResultCode.FAILED), status);
	}

	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpStatus status,
			WebRequest request) {
		return handleExceptionInternal(ex, body, null, status, request);
	}

	private Map<String, Object> handleBindingResult(Map<String, Object> meta, BindingResult bindingResult) {
		List<ValidationResult> validationResultList = Lists.newArrayList();

		bindingResult.getFieldErrors().forEach(fieldError -> {
			validationResultList.add(new ValidationResult(fieldError.getObjectName(),
					fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
		});

		bindingResult.getGlobalErrors().forEach(objectError -> {
			validationResultList.add(new ValidationResult(objectError.getObjectName(), objectError.getDefaultMessage()));
		});

		return handleBindingResult(meta, validationResultList);
	}

	private Map<String, Object> handleBindingResult(Map<String, Object> meta, List<ValidationResult> validationResultList) {
		if (validationResultList != null && !validationResultList.isEmpty()) {
			meta.put("validation", validationResultList);
		}

		return meta;
	}
}
