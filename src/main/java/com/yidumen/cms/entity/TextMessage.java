package com.yidumen.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 微信文本消息
 *
 * @author 蔡迪旻
 */
@Entity
@Table(name = "wechat_message_text")
public class TextMessage extends Message {
    @Column(name = "content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
