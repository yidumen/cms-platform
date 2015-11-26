package com.yidumen.cms.entity;

import java.util.List;

public class NewsMessage extends Message {

    private List<Aritcle> aritcles;

    public List<Aritcle> getAritcles() {
        return aritcles;
    }

    public void setAritcles(List<Aritcle> aritcles) {
        this.aritcles = aritcles;
    }
}
