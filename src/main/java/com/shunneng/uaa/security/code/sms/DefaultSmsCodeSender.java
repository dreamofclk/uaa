package com.shunneng.uaa.security.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shunneng.uaa.config.ApplicationProperties;

/**
 * DefaultSmsSender
 *
 * @author yhq
 * @version 2018/3/21 上午11:10
 */
@Service("defaultSmsCodeSender")
public class DefaultSmsCodeSender implements SmsCodeSender {

    private final Logger log = LoggerFactory.getLogger(DefaultSmsCodeSender.class);

    private final String NATION_CODE = "86";
    private final int TEMPLATE_ID = 97412;
    private final String VALIDATE_TIME = "2";

//    private final ApplicationProperties.Sms sms;
//
    @Autowired
    public DefaultSmsCodeSender(ApplicationProperties applicationProperties) {
    	
    }

    @Override
    public void send(String mobile, String code) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("DefaultSmsCodeSender send mobile:{}, code:{}", mobile, code);
            }
            //调用短信机发送短信
            System.err.println("调用短信机发送短信");
//
//            SmsSingleSender sender = new SmsSingleSender(sms.getAppId(), sms.getAppKey());
//            String[] params = {code, VALIDATE_TIME};
//            SmsSingleSenderResult result = sender.sendWithParam(NATION_CODE, mobile, TEMPLATE_ID, params, sms.getSign(), "", "");
//
//            log.info("SmsService sendSingleSmsCode：{}", result);
        } catch (Exception e) {
            log.error("SmsService sendSingleSmsCode exception:{}", e);
        }
    }
}
