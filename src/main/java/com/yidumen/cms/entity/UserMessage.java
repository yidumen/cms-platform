package com.yidumen.cms.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author 蔡迪旻 <yidumen.com>
 */
@Entity
@Table(name = "web_account_message")
public class UserMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;
    @Column(name = "send_time")
    private Date sendTime;

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "target_account_id")
    private Account target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
    }

}
