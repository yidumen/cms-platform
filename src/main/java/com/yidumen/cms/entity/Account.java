package com.yidumen.cms.entity;

import com.yidumen.dao.constant.AccountGroup;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author 蔡迪旻<yidumen.com>
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Account.findByName",
                query = "SELECT a FROM Account a WHERE a.email = :username OR a.phone = :username")
})
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String email;

    @Column(length = 13)
    private String phone;

    @Basic(optional = false)
    @Column(length = 64)
    private String password;

    @Basic(optional = false)
    @Column(length = 16)
    private String nickname;

    @Column(length = 16)
    private String buddhismname;

    @Column(length = 16)
    private String realname;

    private boolean sex;

    private Date born;

    private String headpic;

    @Column(length = 10)
    private String province;

    @Column(length = 20)
    private String city;

    @Column(length = 20)
    private String area;

    @Basic(optional = false)
    private boolean status;

    @Basic(optional = false)
    private Date createdate;

    @Basic(optional = false)
    private Date lastlogintime;

    @OneToMany(mappedBy = "sender")
    private List<UserMessage> sendedMessages;
    
    @OneToMany(mappedBy = "target")
    private List<UserMessage> receivedMessages;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccessInfo> accessInfo;

    @ManyToMany(mappedBy = "agreedAccount")
    private List<Comment> agreed;

    @OneToOne(cascade = CascadeType.ALL)
    private VerifyInfo verifyInfo;
    @Enumerated(EnumType.ORDINAL)
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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
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

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
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

    public List<Comment> getAgreed() {
        return agreed;
    }

    public void setAgreed(List<Comment> agreed) {
        this.agreed = agreed;
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
