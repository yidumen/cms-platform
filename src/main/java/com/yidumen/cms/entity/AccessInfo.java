package com.yidumen.cms.entity;

import com.yidumen.cms.SiteAccess;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * 封装用户的微博接入信息
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Entity
@Table(name = "web_account_bind_info")
public class AccessInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "auth_id", length = 64)
    private String authId;

    @Column(name = "auth_key", length = 64)
    private String authKey;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "token", nullable = false, length = 50)
    private String token;

    @Column(name = "refresh_key", length = 30)
    private String refreshKey;

    @Column(name = "expires_time", nullable = false)
    private Date expiresTime;

    @Column(name = "target", nullable = false)
    @Enumerated(EnumType.ORDINAL)
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
