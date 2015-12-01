package com.yidumen.cms.entity;

import com.yidumen.cms.SiteAccess;

import java.io.Serializable;
import java.sql.Date;

/**
 * 封装用户的微博接入信息
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class AccessInfo implements Serializable {

    private Long id;

    private Account account;

    private String authId;

    private String authKey;

    private String name;

    private String token;

    private String refreshKey;

    private Date expiresTime;

    private SiteAccess target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshKey() {
        return refreshKey;
    }

    public void setRefreshKey(String refreshKey) {
        this.refreshKey = refreshKey;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    public SiteAccess getTarget() {
        return target;
    }

    public void setTarget(SiteAccess target) {
        this.target = target;
    }

}
