package com.yidumen.cms.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户验证信息
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Entity
@Table(name = "cms_account_verify_info")
public class VerifyInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email_code", length = 10)
    private String emailCode;

    @Column(name = "email_verify_status")
    private boolean emailVerified;

    @Column(name = "mobile_code", length = 50)
    private String mobileCode;

    @Column(name = "mobile_verify_status")
    private boolean mobileVerified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

}
