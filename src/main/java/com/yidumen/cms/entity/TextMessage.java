package com.yidumen.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 微信文本消息
 *
 * @author 蔡迪旻
 */
public class TextMessage extends Message {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
