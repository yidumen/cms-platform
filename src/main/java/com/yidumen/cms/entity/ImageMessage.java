package com.yidumen.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wechat_message_image")
public class ImageMessage extends Message {

    @Column(name = "pic_url")
    private String picUrl;
    @Column(name = "media_id")
    private String mediaId;


    public String getPicUrl() {
        return picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
