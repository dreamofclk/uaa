package com.shunneng.uaa.security.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * ValidateCodeGenerator
 *
 * @author yhq
 * @version 2018/3/22 上午11:33
 */
public interface ValidateCodeGenerator {

    /**
     * generate code
     *
     * @param request
     * @return
     */
    ValidateCode genCode(ServletWebRequest request);

}
