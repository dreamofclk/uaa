package com.shunneng.uaa.security.code.sms;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.shunneng.uaa.security.SecurityConstants;
import com.shunneng.uaa.security.code.ValidateCode;
import com.shunneng.uaa.security.code.ValidateCodeGenerator;
import com.shunneng.uaa.service.util.RandomUtil;

/**
 * SmsValidateCodeGenerator
 *
 * @author yhq
 * @version 2018/3/22 上午11:35
 */
@Component("smsValidateCodeGenerator")
public class SmsValidateCodeGenerator implements ValidateCodeGenerator{

    @Override
    public ValidateCode genCode(ServletWebRequest request) {
        String smsCode = RandomUtil.generateSmsCode();
        return new ValidateCode(smsCode, SecurityConstants.VALIDATE_CODE_TIMEOUT);
    }
}
