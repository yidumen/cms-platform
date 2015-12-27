package com.yidumen.cms.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wechat_message_news_aritcle")
public class Aritcle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "title", length = 32)
    private String title;
    @Column(name = "description", length = 100)
    private String description;
    @Column(name = "pic_url", length = 100)
    private String picUrl;
    @Column(name = "link_url", length = 100)
    private String url;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
