package com.yidumen.cms.entity;

public class ImageMessage extends Message {

    private String picUrl;
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
