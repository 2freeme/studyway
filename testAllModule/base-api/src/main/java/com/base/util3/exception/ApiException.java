package com.base.util3.exception;

import com.base.util3.enums.ResultEnum;

/**
 * @author dingpf
 * @since 2019/4/6
 **/

public class ApiException extends RuntimeException {

    private ResultEnum resultEnum;

    public ApiException(ResultEnum resultEnum) {
        super(String.format("code: %s, message: %s", resultEnum.getCode(), resultEnum.getDesc()));
        this.resultEnum = resultEnum;
    }

    public ApiException(ResultEnum resultEnum, String message) {
        super(String.format("code: %s, message: %s", resultEnum.getCode(), message));
        this.resultEnum = resultEnum;
    }

    public ApiException(ResultEnum resultEnum, Throwable throwable) {
        super(throwable);
        this.resultEnum = resultEnum;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }
}
