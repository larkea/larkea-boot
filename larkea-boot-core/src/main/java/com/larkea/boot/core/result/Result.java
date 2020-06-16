package com.larkea.boot.core.result;

import java.util.Optional;

import com.larkea.boot.core.data.BaseData;
import com.larkea.boot.core.exception.SystemException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@ApiModel(description = "General Return Object")
@Getter
@Setter
@ToString
public class Result<T> implements BaseData {

	@ApiModelProperty(value = "The status of the api call", required = true)
	private boolean success;

	@ApiModelProperty(value = "Error code when failed")
	private int code;

	@ApiModelProperty(value = "Error message when failed")
	private String message;

	@ApiModelProperty("Data when success")
	private T data;

	@ApiModelProperty("Metadata when failed or debug")
	private Object meta;

	private Result() {
		this(SystemResultCode.OK);
	}

	private Result(T data) {
		this(SystemResultCode.OK, data);
	}

	private Result(ResultCode resultCode) {
		this(resultCode, resultCode.getMessage(), null);
	}

	private Result(ResultCode resultCode, String msg) {
		this(resultCode, msg, null);
	}

	private Result(ResultCode resultCode, T data) {
		this(resultCode, resultCode.getMessage(), data);
	}

	private Result(ResultCode resultCode, String message, T data) {
		this.success = SystemResultCode.OK == resultCode;
		this.code = resultCode.getCode();
		this.message = message;
		this.data = data;
	}

	public static boolean isOk(@Nullable Result<?> result) {
		return Optional.ofNullable(result)
				.map(r -> r.code)
				.map(code -> SystemResultCode.OK.code == code)
				.orElse(Boolean.FALSE);
	}

	public static boolean isFailed(@Nullable Result<?> result) {
		return !Result.isOk(result);
	}

	@Nullable
	public static <T> T getData(@Nullable Result<T> result) {
		return Optional.ofNullable(result)
				.filter(r -> r.success)
				.map(r -> r.data)
				.orElse(null);
	}

	/**
	 * Success
	 */
	public static <T> Result<T> ok() {
		return new Result<>();
	}

	public static <T> Result<T> ok(@Nullable T data) {
		return new Result<>(SystemResultCode.OK, data);
	}

	/**
	 * Return error without changing http status code
	 */
	public static <T> Result<T> failed(String message) {
		return new Result<>(SystemResultCode.FAILED, message);
	}

	public static <T> Result<T> failed(ResultCode resultCode) {
		return new Result<>(resultCode);
	}

	public static <T> Result<T> failed(ResultCode resultCode, String message) {
		return new Result<>(resultCode, message);
	}

	/**
	 * Throw exception when failed
	 */
	public static void throwOnFailed(Result<?> result) throws SystemException {
		if (isFailed(result)) {
			throw new SystemException(result);
		}
	}

	public static void throwOnFailed(Result<?> result, ResultCode resultCode) throws SystemException {
		if (isFailed(result)) {
			throw new SystemException(resultCode);
		}
	}

	public static void throwOnFailed(Result<?> result, ResultCode resultCode, String message)
			throws SystemException {
		if (isFailed(result)) {
			throw new SystemException(resultCode, message);
		}
	}

	/**
	 * Throw runtime exception(SystemException) directly
	 */
	public static void throwFailed(ResultCode resultCode) throws SystemException {
		throw new SystemException(resultCode);
	}

	public static void throwFailed(ResultCode resultCode, String message) throws SystemException {
		throw new SystemException(resultCode, message);
	}

	/**
	 * Craft exception and change http status code to 400
	 */
	public static SystemException exception(ResultCode resultCode) {
		return new SystemException(resultCode);
	}

	public static SystemException exception(ResultCode resultCode, String message) {
		return new SystemException(resultCode, message);
	}

	public static SystemException exception(Result<?> result) {
		return new SystemException(result);
	}

}
