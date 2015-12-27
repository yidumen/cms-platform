package com.yidumen.cms.entity;

import com.yidumen.cms.constant.Sex;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 蔡迪旻<yidumen.com>
 */
@Entity
@Table(name = "cms_account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_name")
    private String name;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone", length = 13)
    private String phone;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "nick_name", length = 16)
    private String nickname;

    @Column(name = "buddhism_name", length = 16)
    private String buddhismname;

    @Column(name = "real_name", length = 16)
    private String realname;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "birthday")
    private Date born;

    @Column(name = "head_pic")
    private String headpic;

    @Column(name = "province", length = 10)
    private String province;

    @Column(name = "city", length = 20)
    private String city;

    @Column(name = "area", length = 20)
    private String area;

    @Column(name = "is_enable")
    private Boolean status;

    @Column(name = "create_date")
    private java.util.Date createdate;

    @Column(name = "last_login_time")
    private java.util.Date lastlogintime;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<UserMessage> sendedMessages;

    @OneToMany(mappedBy = "target",cascade = CascadeType.ALL)
    private List<UserMessage> receivedMessages;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccessInfo> accessInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "verify_id")
    private VerifyInfo verifyInfo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "duty_id")
    private Duty duty;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "related_account_permission",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permissions;

    public Account() {
        this.status = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(Permission permission) {
        if (this.permissions == null) {
            this.permissions = new ArrayList<>();
        }
        this.permissions.add(permission);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", buddhismname='" + buddhismname + '\'' +
                ", realname='" + realname + '\'' +
                ", sex=" + sex +
                ", born=" + born +
                ", headpic='" + headpic + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", status=" + status +
                ", createdate=" + createdate +
                ", lastlogintime=" + lastlogintime +
                ", sendedMessages=" + sendedMessages +
                ", receivedMessages=" + receivedMessages +
                ", accessInfo=" + accessInfo +
                ", verifyInfo=" + verifyInfo +
                ", duty=" + duty +
                ", permissions=" + permissions +
                '}';
    }
}
