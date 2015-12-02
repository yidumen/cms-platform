package com.yidumen.cms.entity;


import com.yidumen.cms.MessageType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 蔡迪旻
 */
@Entity
@Table(name = "wechat_replymessage")
public class ReplyMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private MessageType type;
    @ManyToOne
    @JoinColumn(name = "message_id")
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
