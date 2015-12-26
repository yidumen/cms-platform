package com.yidumen.cms.entity;


import com.yidumen.cms.constant.KeyType;
import com.yidumen.cms.constant.MessageType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 订阅者发送消息的关键字
 *
 * @author 蔡迪旻
 */
@Entity
@Table(name = "wechat_replykey")
public class ReplyKey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "keyword", length = 64)
    private String key;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private KeyType type;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "reply_type")
    private MessageType replyType;
    @ManyToOne
    @JoinColumn(name = "reply_message_id")
    private ReplyMessage message;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public KeyType getType() {
        return type;
    }

    public void setType(KeyType type) {
        this.type = type;
    }

    public ReplyMessage getMessage() {
        return message;
    }

    public void setMessage(ReplyMessage message) {
        this.message = message;
    }

    public MessageType getReplyType() {
        return replyType;
    }

    public void setReplyType(MessageType replyType) {
        this.replyType = replyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReplyKey replyKey = (ReplyKey) o;

        if (id != null ? !id.equals(replyKey.id) : replyKey.id != null) return false;
        if (name != null ? !name.equals(replyKey.name) : replyKey.name != null) return false;
        if (key != null ? !key.equals(replyKey.key) : replyKey.key != null) return false;
        if (type != replyKey.type) return false;
        return !(message != null ? !message.equals(replyKey.message) : replyKey.message != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
