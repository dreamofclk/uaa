package com.shunneng.uaa.repository;

import com.shunneng.uaa.domain.OauthValidateCode;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OauthValidateCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OauthValidateCodeRepository extends JpaRepository<OauthValidateCode, Long> {
	
	OauthValidateCode findFirst1ByPrincipal(String principal,Sort sort);

}
