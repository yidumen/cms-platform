package com.yidumen.cms.entity;

import java.io.Serializable;

/**
 * Created by cdm on 2015/10/15.
 */
public class FansGroup implements Serializable {
    private Long id;
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
