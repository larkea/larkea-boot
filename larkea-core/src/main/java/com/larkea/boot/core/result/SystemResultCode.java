package com.larkea.boot.core.result;

import io.swagger.annotations.ApiModel;

/**
 * 系统内置错误码
 */
@ApiModel(description = "系统内置错误码")
public enum SystemResultCode implements ResultCode {

	FAILED(SystemResultCode.FAILED_CODE, "系统异常"),
	OK(SystemResultCode.OK_CODE, "操作成功"),
	PARAM_MISSING(SystemResultCode.PARAM_MISSING_CODE, "缺少必要的请求参数"),
	PARAM_TYPE_ERROR(SystemResultCode.PARAM_TYPE_ERROR_CODE, "请求参数类型错误"),
	PARAM_BINDING_ERROR(SystemResultCode.PARAM_BINDING_ERROR_CODE, "请求参数绑定错误"),
	PARAM_VALID_ERROR(SystemResultCode.PARAM_VALID_ERROR_CODE, "参数不合法"),
	NOT_FOUND(SystemResultCode.NOT_FOUND_CODE, "没找到请求"),
	MSG_NOT_READABLE(SystemResultCode.MSG_NOT_READABLE_CODE, "消息不能读取"),
	MSG_NOT_WRITABLE(SystemResultCode.MSG_NOT_WRITABLE_CODE, "消息不能写入"),
	METHOD_NOT_SUPPORTED(SystemResultCode.METHOD_NOT_SUPPORTED_CODE, "不支持当前请求方法"),
	MEDIA_TYPE_NOT_SUPPORTED(SystemResultCode.MEDIA_TYPE_NOT_SUPPORTED_CODE, "不支持当前媒体类型"),
	MEDIA_TYPE_NOT_ACCEPTABLE(SystemResultCode.MEDIA_TYPE_NOT_ACCEPTABLE_CODE, "不接受的媒体类型"),
	REQ_REJECTED(SystemResultCode.REQ_REJECTED_CODE, "请求被拒绝"),
	REQ_TIMEOUT(SystemResultCode.REQ_TIMEOUT_CODE, "请求超时"),
	ACCESS_TOKEN_EXPIRED(SystemResultCode.ACCESS_TOKEN_EXPIRED_CODE, "访问令牌过期"),
	REFRESH_TOKEN_EXPIRED(SystemResultCode.REFRESH_TOKEN_EXPIRED_CODE, "刷新令牌过期"),
	GRANT_TYPE_ERROR(SystemResultCode.GRANT_TYPE_ERROR_CODE, "授权类型错误"),
	AUTHORIZATION_FAILED(SystemResultCode.AUTHORIZATION_FAILED_CODE, "认证失败"),
	DATA_NOT_EXISTS(SystemResultCode.DATA_NOT_EXISTS_CODE, "数据不存在"),
	DATA_EXISTS(SystemResultCode.DATA_EXISTS_CODE, "数据已存在"),
	DATA_CREATE_FAILED(SystemResultCode.DATA_CREATE_FAILED_CODE, "数据创建失败"),
	DATA_UPDATE_FAILED(SystemResultCode.DATA_UPDATE_FAILED_CODE, "数据更新失败"),
	DATA_DELETE_FAILED(SystemResultCode.DATA_DELETE_FAILED_CODE, "数据删除失败"),
	;

	/**
	 * 通用异常错误码
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

	/**
	 * 数据层错误码
	 */
	public static final int DATA_NOT_EXISTS_CODE = 100100;

	public static final int DATA_EXISTS_CODE = 100101;

	public static final int DATA_CREATE_FAILED_CODE = 100102;

	public static final int DATA_UPDATE_FAILED_CODE = 100103;

	public static final int DATA_DELETE_FAILED_CODE = 100104;

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
