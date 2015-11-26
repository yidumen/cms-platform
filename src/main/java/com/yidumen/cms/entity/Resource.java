package com.yidumen.cms.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * 蔡迪旻
 * 2015年10月21日.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resource {
    private long id;
    private String title;
    private Date createDate;
    private ResourceGroup group;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ResourceGroup getGroup() {
        return group;
    }

    public void setGroup(ResourceGroup group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (id != resource.id) return false;
        if (title != null ? !title.equals(resource.title) : resource.title != null) return false;
        if (createDate != null ? !createDate.equals(resource.createDate) : resource.createDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}
