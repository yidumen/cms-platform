package com.yidumen.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wechat_message_link")
public class LinkMessage extends Message {

    @Column(name = "title", length = 50)
    private String title;
    @Column(name = "description", length = 100)
    private String description;
    @Column(name = "link_url", length = 100)
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
