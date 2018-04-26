package com.shunneng.uaa.security.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;

import com.shunneng.uaa.domain.OauthValidateCode;
import com.shunneng.uaa.repository.OauthValidateCodeRepository;
import com.shunneng.uaa.security.SecurityConstants;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Objects;

/**
 * AbstractValidateCodeProcessor
 *
 * @author yhq
 * @version 2018/3/22 上午10:40
 */
@Component
public class OauthValidateCodeProcessor implements ValidateCodeRepository {

	private final Logger log = LoggerFactory.getLogger(OauthValidateCodeProcessor.class);

	private final OauthValidateCodeRepository oauthValidateCodeRepository;

	public OauthValidateCodeProcessor(OauthValidateCodeRepository oauthValidateCodeRepository) {
		Assert.notNull(oauthValidateCodeRepository, "oauthValidateCodeRepository required");
		this.oauthValidateCodeRepository = oauthValidateCodeRepository;
	}

	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
		String principal = request.getParameter(SecurityConstants.SPRING_SECURITY_FORM_MOBILE_KEY);
		OauthValidateCode oauthValidateCode = new OauthValidateCode(code.getCode(), principal, code.getTimestamp().toLocalDateTime(), validateCodeType.getParamNameOnValidate());
		oauthValidateCode = oauthValidateCodeRepository.saveAndFlush(oauthValidateCode);
		log.info("oauthValidateCode:{}", oauthValidateCode.toString());
	}

	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
		String principal = request.getParameter(SecurityConstants.SPRING_SECURITY_FORM_MOBILE_KEY);
		Sort s = new Sort(Direction.DESC, "createdDate");
		OauthValidateCode oauthValidateCode = oauthValidateCodeRepository.findFirst1ByPrincipal(principal,s);
		ValidateCode v = null;
		if(Objects.nonNull(oauthValidateCode)) {
			v = new ValidateCode(oauthValidateCode.getCode(), oauthValidateCode.getExpireTime());
		}
		return v;
	}

	@Override
	public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
		String principal = request.getParameter(SecurityConstants.SPRING_SECURITY_FORM_MOBILE_KEY);
//		int remove = jdbcTemplate.update(deleteCodeSql,
//				new Object[] { principal, validateCodeType.getParamNameOnValidate() },
//				new int[] { Types.VARCHAR, Types.VARCHAR });
		log.info("remove principal:{}", principal);
	}

}
