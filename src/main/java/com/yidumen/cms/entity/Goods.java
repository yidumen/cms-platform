package com.yidumen.cms.entity;

import com.yidumen.cms.constant.GoodsStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author pholance
 */
@Entity
@Table(name = "web_goods")
public class Goods implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "create_date")
    private Date createdate;
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private GoodsStatus status;
    @Column(name = "post_code")
    private String postCode;
    @Column(name = "post_number")
    private String postNumber;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public GoodsStatus getStatus() {
        return status;
    }

    public void setStatus(GoodsStatus status) {
        this.status = status;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

}
