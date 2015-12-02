package com.yidumen.cms.entity;

import javax.persistence.*;

/**
 * @author 蔡迪旻
 *         2015年11月15日
 */
@Entity
@Table(name = "resource_page")
public class Page extends Resource {
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        if (content != null ? !content.equals(page.content) : page.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }
}
