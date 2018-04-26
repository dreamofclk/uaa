package com.shunneng.uaa.security.code.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * DefaultEmailCodeSender
 *
 * @author yhq
 * @version 2018/3/21 上午11:10
 */
@Service("defaultEmailCodeSender")
public class DefaultEmailCodeSender implements EmailCodeSender {

    private final Logger log = LoggerFactory.getLogger(DefaultEmailCodeSender.class);

//    private final MailService mailService;
//
//    public DefaultEmailCodeSender(MailService mailService) {
//        this.mailService = mailService;
//    }
//
    @Override
    public void send(String email, String code) {
        log.info("DefaultEmailCodeSender send email:{},code:{}", email, code);
        //todo
    }

    @Override
    public void send(String email, String code, String url) {
        log.info("DefaultEmailCodeSender send email:{},code:{}, url:{}", email, code, url);
        //todo
    }
}
