package com.shunneng.uaa.security.code;

import org.springframework.security.core.AuthenticationException;


/**
 * ValidateCodeException
 *
 * @author yhq
 * @version 2018/3/22 上午10:40
 */
public class ValidateCodeException extends AuthenticationException {

    private Integer code;

    public ValidateCodeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
