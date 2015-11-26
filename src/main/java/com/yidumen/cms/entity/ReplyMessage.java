package com.yidumen.cms.entity;

import com.yuntaisi.wechat.MessageType;

import java.io.Serializable;

/**
 * @author 蔡迪旻
 */
public class ReplyMessage implements Serializable {

    private Long id;
    private String name;
    private MessageType type;
    private Message message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
