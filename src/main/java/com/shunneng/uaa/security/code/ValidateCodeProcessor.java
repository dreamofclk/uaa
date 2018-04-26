package com.shunneng.uaa.security.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * ValidateCodeProcessor
 *
 * @author yhq
 * @version 2018/3/22 上午10:40
 */
public interface ValidateCodeProcessor {

    /**
     * create code
     *
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * code code
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest) throws ValidateCodeException;

}
