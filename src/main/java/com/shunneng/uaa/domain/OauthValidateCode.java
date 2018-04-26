package com.shunneng.uaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.shunneng.uaa.domain.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A OauthValidateCode.
 */
@Entity
@Table(name = "oauth_validate_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OauthValidateCode extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Size(max = 100)
    @Column(name = "principal", length = 100)
    private String principal;

    @Column(name = "expire_time")
    private ZonedDateTime expireTime;

    @Size(max = 20)
    @Column(name = "code_type", length = 20)
    private String codeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public OauthValidateCode code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrincipal() {
        return principal;
    }

    public OauthValidateCode principal(String principal) {
        this.principal = principal;
        return this;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public LocalDateTime getExpireTime() {
        return expireTime.toLocalDateTime();
    }

    public OauthValidateCode expireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public void setExpireTime(ZonedDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public String getCodeType() {
        return codeType;
    }

    public OauthValidateCode codeType(String codeType) {
        this.codeType = codeType;
        return this;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OauthValidateCode oauthValidateCode = (OauthValidateCode) o;
        if (oauthValidateCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), oauthValidateCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OauthValidateCode{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", principal='" + getPrincipal() + "'" +
            ", expireTime='" + getExpireTime() + "'" +
            ", codeType='" + getCodeType() + "'" +
            "}";
    }

	public OauthValidateCode(String code, String principal, LocalDateTime expireTime, String codeType) {
		this.code = code;
		this.principal = principal;
		this.expireTime = ZonedDateTime.of(expireTime, ZoneId.systemDefault());
		this.codeType = codeType;
	}

	public OauthValidateCode() {
	}
    
    
}
