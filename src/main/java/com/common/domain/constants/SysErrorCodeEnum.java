package com.common.domain.constants;

import lombok.Getter;

@Getter
public enum SysErrorCodeEnum {

    NONE("NONE_ERROR", "未知错误"),

    ERR_SYSTEM("ERR_SYSTEM", "系统错误"),

    ERR_SERVICE_STOP("ERR_SERVICE_STOP", "服务不可用"),

    ERR_AUTH_LIMIT("ERR_AUTH_LIMIT", "访问权限受限"),

    ERR_ILLEGAL_PARAM("ERR_ILLEGAL_PARAM", "参数值非法"),

    ERR_REST_FAIL("ERR_REST_FAIL", "服务接口调用失败"),

    CODE_ERROR("CODE_ERROR", "code错误"),

    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", "token不存在"),

    TOKEN_INVALID("TOKEN_INVALID", "token无效"),

    SIGN_NULL("SIGN_NULL", "签名为空"),

    SIGN_ERROR("SIGN_ERROR", "签名错误"),

    TOKEN_IS_NULL("TOKEN_IS_NULL", "token为空");

    private String code;
    private String msg;

    SysErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }




}
