package com.yidumen.cms.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 蔡迪旻
 * 2015年12月02日
 */
@Entity
@Table(name = "wechat_fansgroup")
public class FansGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 20)
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FansGroup fansGroup = (FansGroup) o;

        if (id != fansGroup.id) return false;
        return name.equals(fansGroup.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}
