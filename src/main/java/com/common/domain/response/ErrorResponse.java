package com.common.domain.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @auth guozhenhua
 * @date 2018/11/08
 */
@Getter
@Setter
public class ErrorResponse implements java.io.Serializable {

    private String code;

    private String msg;

    public ErrorResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
