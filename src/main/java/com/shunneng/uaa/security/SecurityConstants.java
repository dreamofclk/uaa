package com.shunneng.uaa.security;

/**
 * SecurityConstants
 *
 * @author yhq
 * @version 2018/3/22 上午10:40
 */
public interface SecurityConstants {

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 短信验证码登录链接
     */
    String DEFAULT_SIGN_IN_URL_SMS = "/auth/sms";

    /**
     * 手机号登录 key
     */
    String SPRING_SECURITY_FORM_MOBILE_KEY = "username";

    /**
     * 验证码有效期 单位seconds
     */
    int VALIDATE_CODE_TIMEOUT = 60 * 5;

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "image";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "sms";
}
