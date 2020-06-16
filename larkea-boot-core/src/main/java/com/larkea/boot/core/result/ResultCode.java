package com.larkea.boot.core.result;

import com.larkea.boot.core.data.BaseData;

public interface ResultCode extends BaseData {

    int getCode();

    String getMessage();

}
