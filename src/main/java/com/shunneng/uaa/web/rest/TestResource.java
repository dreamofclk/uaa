package com.shunneng.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shunneng.uaa.security.code.sms.DefaultSmsCodeSender;
import com.shunneng.uaa.security.code.sms.SmsCodeSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    private final SmsCodeSender smsCodeSender;

    public TestResource(DefaultSmsCodeSender smsCodeSender) {
        this.smsCodeSender = smsCodeSender;
    }

    @GetMapping("/test")
    @Timed
    public void test() {
        smsCodeSender.send("13521895124", "123121");
    }

}
