package com.yidumen.cms.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.yidumen.cms.JacksonView;

import javax.persistence.*;
import java.util.Date;

/**
 * 蔡迪旻
 * 2015年10月21日.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", length = 50)
    private String title;
    @Column(name = "create_date")
    private Date createDate;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private ResourceGroup group;

    @JsonView(JacksonView.MostLess.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(JacksonView.Less.class)
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
