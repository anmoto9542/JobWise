package com.example.jobWise.exception;

import com.example.jobWise.enums.StatusCodeEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final StatusCodeEnum statusCodeEnum;

    public CustomException(StatusCodeEnum statusCodeEnum) {
        super(statusCodeEnum.getMessage());
        this.statusCodeEnum = statusCodeEnum;
    }

//    public CustomException(StatusCodeEnum statusCodeEnum, String message) {
//        super(message);
//        this.statusCodeEnum = statusCodeEnum;
//    }

//    public String getCode() {
//        return statusCodeEnum.getCode();
//    }

}
