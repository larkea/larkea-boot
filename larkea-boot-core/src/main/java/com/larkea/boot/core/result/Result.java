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

@ApiModel(description = "返回参数")
@Getter
@Setter
@ToString
public class Result<T> implements BaseData {

	@ApiModelProperty(value = "表示是否调用成功。true表示调用成功，false表示调用失败。", required = true)
	private boolean success;

	@ApiModelProperty(value = "调用失败时，返回的错误码")
	private int code;

	@ApiModelProperty(value = "调用失败时，返回的出错信息")
	private String message;

	@ApiModelProperty("调用成功时返回的信息")
	private T data;

	@ApiModelProperty("元数据, 调用失败时，可以返回更多数据")
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
	 * 返回成功
	 */
	public static <T> Result<T> ok() {
		return new Result<>();
	}

	public static <T> Result<T> ok(@Nullable T data) {
		return new Result<>(SystemResultCode.OK, data);
	}

	/**
	 * 返回错误信息, 不改变 http 状态码
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
	 * 错误时抛出异常
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
	 * 直接抛出失败异常
	 */
	public static void throwFailed(ResultCode resultCode) throws SystemException {
		throw new SystemException(resultCode);
	}

	public static void throwFailed(ResultCode resultCode, String message) throws SystemException {
		throw new SystemException(resultCode, message);
	}

	/**
	 * 构造异常, http 状态码 为 400
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
