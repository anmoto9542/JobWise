package com.example.jobWise.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    SUCCESS("0000", "成功"),
    ERR9999("9999", "請求參數錯誤"),
    ERR9901("9901", "帳戶已存在"),
    ERR9902("9902", "登入資訊錯誤，請重新確認"),
    ERR9903("9903", "帳戶資訊查詢錯誤");

    private final String code;
    private final String message;

    public static StatusCodeEnum findByCode(String code) {
        for (StatusCodeEnum codeEnum : StatusCodeEnum.values()) {
            if (codeEnum.getCode().equals(code)) {
                return codeEnum;
            }
        }
        return StatusCodeEnum.ERR9999;
    }
}
