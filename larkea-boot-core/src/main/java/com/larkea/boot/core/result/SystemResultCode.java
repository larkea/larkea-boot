package com.larkea.boot.core.result;

import io.swagger.annotations.ApiModel;

/**
 * System Error Code
 */
@ApiModel(description = "System error code")
public enum SystemResultCode implements ResultCode {

	FAILED(SystemResultCode.FAILED_CODE, "Failed"),
	OK(SystemResultCode.OK_CODE, "Success"),
	PARAM_MISSING(SystemResultCode.PARAM_MISSING_CODE, "Missing required parameter(s)"),
	PARAM_TYPE_ERROR(SystemResultCode.PARAM_TYPE_ERROR_CODE, "Wrong type of parameter"),
	PARAM_BINDING_ERROR(SystemResultCode.PARAM_BINDING_ERROR_CODE, "Parameter binding error"),
	PARAM_VALID_ERROR(SystemResultCode.PARAM_VALID_ERROR_CODE, "Parameter validation failed"),
	NOT_FOUND(SystemResultCode.NOT_FOUND_CODE, "Not found"),
	MSG_NOT_READABLE(SystemResultCode.MSG_NOT_READABLE_CODE, "Message is not readable"),
	MSG_NOT_WRITABLE(SystemResultCode.MSG_NOT_WRITABLE_CODE, "Message is not writable"),
	METHOD_NOT_SUPPORTED(SystemResultCode.METHOD_NOT_SUPPORTED_CODE, "Method is not supported"),
	MEDIA_TYPE_NOT_SUPPORTED(SystemResultCode.MEDIA_TYPE_NOT_SUPPORTED_CODE, "Media type is not supported"),
	MEDIA_TYPE_NOT_ACCEPTABLE(SystemResultCode.MEDIA_TYPE_NOT_ACCEPTABLE_CODE, "Media type is not acceptable"),
	REQ_REJECTED(SystemResultCode.REQ_REJECTED_CODE, "Request is rejected"),
	REQ_TIMEOUT(SystemResultCode.REQ_TIMEOUT_CODE, "Request is timeout"),
	ACCESS_TOKEN_EXPIRED(SystemResultCode.ACCESS_TOKEN_EXPIRED_CODE, "Access token is expired"),
	REFRESH_TOKEN_EXPIRED(SystemResultCode.REFRESH_TOKEN_EXPIRED_CODE, "Refresh token is expired"),
	ACCESS_TOKEN_INVALID(SystemResultCode.ACCESS_TOKEN_INVALID_CODE, "Access token is invalid"),
	REFRESH_TOKEN_INVALID(SystemResultCode.REFRESH_TOKEN_INVALID_CODE, "Refresh token is invalid"),
	NO_PERMISSION(SystemResultCode.NO_PERMISSION_CODE, "No permission"),

	GRANT_TYPE_ERROR(SystemResultCode.GRANT_TYPE_ERROR_CODE, "Grant type is not supported"),
	AUTHORIZATION_FAILED(SystemResultCode.AUTHORIZATION_FAILED_CODE, "Authorization failed"),
	DATA_NOT_EXISTS(SystemResultCode.DATA_NOT_EXISTS_CODE, "Data is not existed"),
	DATA_EXISTS(SystemResultCode.DATA_EXISTS_CODE, "Data is existed"),
	DATA_CREATE_FAILED(SystemResultCode.DATA_CREATE_FAILED_CODE, "Create data failed"),
	DATA_UPDATE_FAILED(SystemResultCode.DATA_UPDATE_FAILED_CODE, "Update data failed"),
	DATA_DELETE_FAILED(SystemResultCode.DATA_DELETE_FAILED_CODE, "Delete data failed"),
	DATA_QUERY_FAILED(SystemResultCode.DATA_QUERY_FAILED_CODE, "Query data failed"),
	;

	/**
	 * General Error Code
	 */
	public static final int FAILED_CODE = -1;

	public static final int OK_CODE = 1;

	public static final int PARAM_MISSING_CODE = 100000;

	public static final int PARAM_TYPE_ERROR_CODE = 100001;

	public static final int PARAM_BINDING_ERROR_CODE = 100002;

	public static final int PARAM_VALID_ERROR_CODE = 100003;

	public static final int NOT_FOUND_CODE = 100004;

	public static final int MSG_NOT_READABLE_CODE = 100005;

	public static final int METHOD_NOT_SUPPORTED_CODE = 100006;

	public static final int MEDIA_TYPE_NOT_SUPPORTED_CODE = 100007;

	public static final int MEDIA_TYPE_NOT_ACCEPTABLE_CODE = 100008;

	public static final int REQ_REJECTED_CODE = 100009;

	public static final int ACCESS_TOKEN_EXPIRED_CODE = 100010;

	public static final int REFRESH_TOKEN_EXPIRED_CODE = 100011;

	public static final int GRANT_TYPE_ERROR_CODE = 100012;

	public static final int AUTHORIZATION_FAILED_CODE = 100013;

	public static final int MSG_NOT_WRITABLE_CODE = 100014;

	public static final int REQ_TIMEOUT_CODE = 100015;

	public static final int NO_PERMISSION_CODE = 100016;

	public static final int ACCESS_TOKEN_INVALID_CODE = 100017;

	public static final int REFRESH_TOKEN_INVALID_CODE = 100018;


	/**
	 * Data Layer Error Code
	 */
	public static final int DATA_NOT_EXISTS_CODE = 100100;

	public static final int DATA_EXISTS_CODE = 100101;

	public static final int DATA_CREATE_FAILED_CODE = 100102;

	public static final int DATA_UPDATE_FAILED_CODE = 100103;

	public static final int DATA_DELETE_FAILED_CODE = 100104;

	public static final int DATA_QUERY_FAILED_CODE = 100105;

	final int code;

	final String message;

	SystemResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
