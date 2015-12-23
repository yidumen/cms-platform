package com.yidumen.cms.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wechat_message_news")
public class NewsMessage extends Message {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "related_news_aritcle",joinColumns = @JoinColumn(name = "news_id",referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "aritcle_id",referencedColumnName = "id"))
    private List<Aritcle> aritcles;

    public List<Aritcle> getAritcles() {
        return aritcles;
    }

    public void setAritcles(List<Aritcle> aritcles) {
        this.aritcles = aritcles;
    }
}
