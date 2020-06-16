package com.larkea.boot.boot.controller;

import com.larkea.boot.core.result.Result;
import com.larkea.boot.core.result.ResultCode;
import com.larkea.boot.core.result.SystemResultCode;

public abstract class BaseController {

    public <T> Result<T> ok() {
        return Result.ok();
    }

    public <T> Result<T> ok(T data) {
        return Result.ok(data);
    }

    public <T> Result<T> failed(String msg) {
        return Result.failed(SystemResultCode.FAILED, msg);
    }

    public <T> Result<T> failed(ResultCode resultCode) {
        return Result.failed(resultCode);
    }

    public <T> Result<T> failed(ResultCode resultCode, String msg) {
        return Result.failed(resultCode, msg);
    }
}
