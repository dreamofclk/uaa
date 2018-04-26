package com.shunneng.uaa.security.code.mail;

/**
 * EmailCodeSender
 *
 * @author yhq
 * @version 2018/3/21 上午11:05
 */
public interface EmailCodeSender {

    void send(String email, String code);

    void send(String email, String code, String url);
}
