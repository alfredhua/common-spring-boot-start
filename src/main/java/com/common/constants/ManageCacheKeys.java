package com.common.constants;

import lombok.Getter;

/**
 * @auth guozhenhua
 * @date 2018/12/18
 */

@Getter
public enum ManageCacheKeys {


    ACCESS_TOKEN("access_token:",7000) ,     //微信token
    WEI_XIN_IP("wei_xin_ip:",24*60*60*30) ,     //微信ip

    ADMIN_INFO("admin_info:",2*60*60),       //管理端用户信息
    ADMIN_CAPTCHA("admin_captcha:",10*60);   //图片验证码

    private String key;

    private Long timeOut;


    ManageCacheKeys(String key, long timeOut) {
        this.key = key;
        this.timeOut = timeOut;
    }
}
