package com.larkea.boot.core.exception;

import com.larkea.boot.core.result.Result;
import com.larkea.boot.core.result.ResultCode;
import org.springframework.lang.Nullable;

public class SystemException extends RuntimeException {
	static final long serialVersionUID = -672253298756571471L;

	private Result<?> result;

	public SystemException(Result<?> result) {
		super(result.getMessage());
		this.result = result;
	}

	public SystemException(ResultCode resultCode) {
		this(resultCode, resultCode.getMessage());
	}

	public SystemException(ResultCode resultCode, String message) {
		super(message);
		this.result = Result.failed(resultCode, message);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		this(cause.getMessage(), cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	@Nullable
	@SuppressWarnings("unchecked")
	public <T> Result<T> getResult() {
		return (Result<T>) result;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}

}
