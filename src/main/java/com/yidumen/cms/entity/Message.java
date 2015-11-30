package com.yidumen.cms.entity;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private Long id;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (getId() != message.getId()) return false;
        return !(getCreateTime() != null ? !getCreateTime().equals(message.getCreateTime()) : message.getCreateTime() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        return result;
    }
}
