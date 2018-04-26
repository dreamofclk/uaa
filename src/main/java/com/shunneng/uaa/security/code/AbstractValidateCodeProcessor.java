package com.shunneng.uaa.security.code;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.shunneng.uaa.security.SecurityConstants;

import java.util.Map;

/**
 * AbstractValidateCodeProcessor
 *
 * @author yhq
 * @version 2018/3/22 上午10:40
 */
public abstract class AbstractValidateCodeProcessor<V extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * collection all {@link ValidateCodeGenerator} interface implementation。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private OauthValidateCodeProcessor oauthValidateCodeProcessor;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        V validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 根据Holder返回得CodeProcessor类型来获取验证码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "ValidateCodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    private V generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (V) validateCodeGenerator.genCode(request);
    }

    /**
     * 保存验证码
     */
    private void save(ServletWebRequest request, V validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), SecurityConstants.VALIDATE_CODE_TIMEOUT);
        oauthValidateCodeProcessor.remove(request, getValidateCodeType(request));
        oauthValidateCodeProcessor.save(request, code, getValidateCodeType(request));
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, V validateCode) throws Exception;

    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType codeType = getValidateCodeType(request);

        V codeSaved = (V) oauthValidateCodeProcessor.get(request, codeType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), codeType + "验证码的值不能为空");
        }

        if (codeSaved == null) {
            throw new ValidateCodeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), codeType + "验证码不存在");
        }

        if (codeSaved.isExpired()) {
            oauthValidateCodeProcessor.remove(request, codeType);
            throw new ValidateCodeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeSaved.getCode(), codeInRequest)) {
            throw new ValidateCodeException(HttpStatus.INTERNAL_SERVER_ERROR.value(), codeType + "验证码不匹配");
        }

        oauthValidateCodeProcessor.remove(request, codeType);

    }

//    @Bean
//    public JdbcValidateCodeRepository oauthValidateCodeProcessor {
//        return new JdbcValidateCodeRepository(dataSource);
//    }

}
