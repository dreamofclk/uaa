package com.shunneng.uaa.security.code;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * ValidateCode
 *
 * @author yhq
 * @version 2018/3/22 上午10:40
 */
public class ValidateCode implements Serializable {

    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public Timestamp getTimestamp(){
        return Timestamp.valueOf(expireTime);
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
