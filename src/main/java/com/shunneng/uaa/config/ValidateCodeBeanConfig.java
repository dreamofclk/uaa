package com.shunneng.uaa.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shunneng.uaa.security.code.sms.DefaultSmsCodeSender;
import com.shunneng.uaa.security.code.sms.SmsCodeSender;

@Configuration
public class ValidateCodeBeanConfig {


    private final ApplicationProperties applicationProperties;

    public ValidateCodeBeanConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * 短信验证码发送器
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender(applicationProperties);
    }
}
