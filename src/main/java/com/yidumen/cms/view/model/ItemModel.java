package com.yidumen.cms.view.model;

/**
 *
 * @author 蔡迪旻
 */
public final class ItemModel {

    private String name;
    private String uri;

    public ItemModel(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
