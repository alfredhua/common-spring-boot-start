package com.common.constants;

import lombok.Getter;

/**
 * @auth guozhenhua
 * @date 2018/12/18
 */

@Getter
public enum SiteCacheKeys {


    USER_INFO("user:",2*60*60) ,     //前端用户信息
    BANNER_LIST("banner_list:",60*60),    //前端bannerList
    PHONE_CODE("phone:",5*60),          //手机验证码
    CAPTCHA("captcha:",10*60),          //前端图片验证码
    VERIFY("verify:",30*60);

    private String key;

    private Long timeOut;


    SiteCacheKeys(String key, long timeOut) {
        this.key = key;
        this.timeOut = timeOut;
    }
}
