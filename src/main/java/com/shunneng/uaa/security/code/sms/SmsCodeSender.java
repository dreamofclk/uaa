package com.shunneng.uaa.security.code.sms;

/**
 * SmsSender
 *
 * @author yhq
 * @version 2018/3/21 上午11:05
 */
public interface SmsCodeSender {

    void send(String mobile, String code);
}
