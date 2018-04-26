package com.shunneng.uaa.security.code.sms;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.shunneng.uaa.security.SecurityConstants;
import com.shunneng.uaa.security.code.AbstractValidateCodeProcessor;
import com.shunneng.uaa.security.code.ValidateCode;

/**
 * SmsValidateCodeProcessor
 *
 * @author yhq
 * @version 2018/3/22 上午11:16
 */
@Component("smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    private final SmsCodeSender smsCodeSender;

    public SmsValidateCodeProcessor(SmsCodeSender smsCodeSender) {
        this.smsCodeSender = smsCodeSender;
    }

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.SPRING_SECURITY_FORM_MOBILE_KEY;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
