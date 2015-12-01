package com.yidumen.cms.entity;


import com.yidumen.cms.Sex;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信订阅者列表
 *
 * @author 蔡迪旻
 */
public class Fans implements Serializable {
    private long id;
    private String openId;
    private String nickName;
    private Sex sex;
    private String country;
    private String city;
    private String province;
    private String headImageUrl;
    private Date subscribe_time;
    private String unionId;
    private String remark;
    private FansGroup group;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public Date getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(Date subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FansGroup getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fans fans = (Fans) o;

        if (id != fans.id) return false;
        if (!openId.equals(fans.openId)) return false;
        if (nickName != null ? !nickName.equals(fans.nickName) : fans.nickName != null) return false;
        if (sex != fans.sex) return false;
        if (country != null ? !country.equals(fans.country) : fans.country != null) return false;
        if (city != null ? !city.equals(fans.city) : fans.city != null) return false;
        if (province != null ? !province.equals(fans.province) : fans.province != null) return false;
        if (headImageUrl != null ? !headImageUrl.equals(fans.headImageUrl) : fans.headImageUrl != null) return false;
        if (subscribe_time != null ? !subscribe_time.equals(fans.subscribe_time) : fans.subscribe_time != null)
            return false;
        if (unionId != null ? !unionId.equals(fans.unionId) : fans.unionId != null) return false;
        if (remark != null ? !remark.equals(fans.remark) : fans.remark != null) return false;
        return !(group != null ? !group.equals(fans.group) : fans.group != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + openId.hashCode();
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (headImageUrl != null ? headImageUrl.hashCode() : 0);
        result = 31 * result + (subscribe_time != null ? subscribe_time.hashCode() : 0);
        result = 31 * result + (unionId != null ? unionId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }

    public void setGroup(FansGroup group) {
        this.group = group;
    }
}
