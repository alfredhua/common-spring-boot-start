package com.common.util;

import com.common.domain.constants.SysErrorCodeEnum;
import com.common.domain.response.ErrorResponse;

public class ExceptionUtils {

    private ExceptionUtils() {}

    /**
     * 未知错误
     * @return
     */
    public static ErrorResponse noneException() {
        return new ErrorResponse(SysErrorCodeEnum.NONE.getCode(), SysErrorCodeEnum.NONE.getMsg());
    }

    /**
     * 系统错误
     * @return
     */
    public static ErrorResponse systemException() {
        return new ErrorResponse(SysErrorCodeEnum.ERR_SYSTEM.getCode(), SysErrorCodeEnum.ERR_SYSTEM.getMsg());
    }

    /**
     * 服务不可用
     * @return
     */
    public static ErrorResponse serviceStopException() {
        return new ErrorResponse(SysErrorCodeEnum.ERR_SERVICE_STOP.getCode(), SysErrorCodeEnum.ERR_SERVICE_STOP.getMsg());
    }

    /**
     * 没有访问权限
     * @return
     */
    public static ErrorResponse authLimitException() {
        return new ErrorResponse(SysErrorCodeEnum.ERR_AUTH_LIMIT.getCode(), SysErrorCodeEnum.ERR_AUTH_LIMIT.getMsg());
    }
    /**
     * 参数非法错误
     * @return
     */
    public static ErrorResponse paramException() {
        return new ErrorResponse(SysErrorCodeEnum.ERR_ILLEGAL_PARAM.getCode(), SysErrorCodeEnum.ERR_ILLEGAL_PARAM.getMsg());
    }

    /**
     * 接口访问失败
     * @return
     */
    public static ErrorResponse failException() {
        return new ErrorResponse(SysErrorCodeEnum.ERR_REST_FAIL.getCode(), SysErrorCodeEnum.ERR_REST_FAIL.getMsg());
    }

    /**
     * 签名为空错误
     * @return
     */
    public static ErrorResponse signNullException() {
        return new ErrorResponse(SysErrorCodeEnum.SIGN_NULL.getCode(), SysErrorCodeEnum.SIGN_NULL.getMsg());
    }

    /**
     * 签名验证错误
     * @return
     */
    public static ErrorResponse signErrorException() {
        return new ErrorResponse(SysErrorCodeEnum.SIGN_ERROR.getCode(), SysErrorCodeEnum.SIGN_ERROR.getMsg());
    }


    /**
     * token为空
     * @return
     */
    public static ErrorResponse tokenNullException() {
        return new ErrorResponse(SysErrorCodeEnum.TOKEN_IS_NULL.getCode(), SysErrorCodeEnum.TOKEN_IS_NULL.getMsg());
    }

    /**
     * token验证失败
     * @return
     */
    public static ErrorResponse tokenInvalidException() {
        return new ErrorResponse(SysErrorCodeEnum.TOKEN_INVALID.getCode(), SysErrorCodeEnum.TOKEN_INVALID.getMsg());
    }

}