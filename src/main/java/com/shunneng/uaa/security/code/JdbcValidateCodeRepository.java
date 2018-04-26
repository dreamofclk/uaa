package com.shunneng.uaa.security.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;

import com.shunneng.uaa.security.SecurityConstants;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * AbstractValidateCodeProcessor
 *
 * @author yhq
 * @version 2018/3/22 上午10:40
 */
@Component
public class JdbcValidateCodeRepository implements ValidateCodeRepository {

    private final Logger log = LoggerFactory.getLogger(JdbcValidateCodeRepository.class);

    private static final String DEFAULT_VALIDATE_CODE_INSERT_STATEMENT = "insert into oauth_validate_code (code, principal, expire_time, type) values (?, ?, ?, ?)";

    private static final String DEFAULT_VALIDATE_CODE_SELECT_STATEMENT = "select code, expire_time from oauth_validate_code where principal = ? and type = ?";

    private static final String DEFAULT_VALIDATE_CODE_DELETE_STATEMENT = "delete from oauth_validate_code where principal = ? and type = ?";

    private String insertCodeSql = DEFAULT_VALIDATE_CODE_INSERT_STATEMENT;
    private String selectCodeSql = DEFAULT_VALIDATE_CODE_SELECT_STATEMENT;
    private String deleteCodeSql = DEFAULT_VALIDATE_CODE_DELETE_STATEMENT;

    private final JdbcTemplate jdbcTemplate;

    public JdbcValidateCodeRepository(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        String principal = request.getParameter(SecurityConstants.SPRING_SECURITY_FORM_MOBILE_KEY);
        int update = jdbcTemplate.update(insertCodeSql,
            new Object[]{code.getCode(), principal, code.getTimestamp(), validateCodeType.getParamNameOnValidate()},
            new int[]{
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR
            });
        log.info("save size:{}", update);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String principal = request.getParameter(SecurityConstants.SPRING_SECURITY_FORM_MOBILE_KEY);
        return jdbcTemplate
            .queryForObject(selectCodeSql, (rs, rowNum) ->
                    new ValidateCode(
                        rs.getString(1),
                        rs.getTimestamp(2).toLocalDateTime()),
                principal,
                validateCodeType.getParamNameOnValidate());
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String principal = request.getParameter(SecurityConstants.SPRING_SECURITY_FORM_MOBILE_KEY);
        int remove = jdbcTemplate.update(deleteCodeSql, new Object[]{principal, validateCodeType.getParamNameOnValidate()},
            new int[]{
                Types.VARCHAR, Types.VARCHAR
            });
        log.info("remove size:{}", remove);
    }

}
