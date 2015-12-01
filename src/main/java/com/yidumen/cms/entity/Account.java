package com.yidumen.cms.entity;

import com.yidumen.cms.AccountGroup;
import com.yidumen.cms.Sex;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @author 蔡迪旻<yidumen.com>
 */
public class Account implements Serializable {

    private Long id;

    private String email;

    private String phone;

    private String password;

    private String nickname;

    private String buddhismname;

    private String realname;

    private Sex sex;

    private Date born;

    private String headpic;

    private String province;

    private String city;

    private String area;

    private boolean status;

    private java.util.Date createdate;

    private java.util.Date lastlogintime;

    private List<UserMessage> sendedMessages;

    private List<UserMessage> receivedMessages;

    private List<AccessInfo> accessInfo;

    private VerifyInfo verifyInfo;
    private AccountGroup userGroup;

    public Account() {
        this.status = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBuddhismname() {
        return buddhismname;
    }

    public void setBuddhismname(String buddhismname) {
        this.buddhismname = buddhismname;
    }

    public String getRealname() {
        return realname;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 获取用户所属省份
     *
     * @return 用户所属省份名
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置用户所属省份
     *
     * @param province 省份名
     */
    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public java.util.Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(java.util.Date createdate) {
        this.createdate = createdate;
    }

    public java.util.Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(java.util.Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public List<UserMessage> getSendedMessages() {
        return sendedMessages;
    }

    public void setSendedMessages(List<UserMessage> sendedMessages) {
        this.sendedMessages = sendedMessages;
    }

    public List<UserMessage> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<UserMessage> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<AccessInfo> getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(List<AccessInfo> accessInfo) {
        this.accessInfo = accessInfo;
    }

    public VerifyInfo getVerifyInfo() {
        return verifyInfo;
    }

    public void setVerifyInfo(VerifyInfo verifyInfo) {
        this.verifyInfo = verifyInfo;
    }

    public AccountGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(AccountGroup userGroup) {
        this.userGroup = userGroup;
    }

}
