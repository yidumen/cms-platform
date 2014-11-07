package com.yidumen.cms.view.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public final class MenuModel {

    private String name;
    private List<ItemGroup> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemGroup> getItems() {
        return items;
    }

    public void setItems(List<ItemGroup> items) {
        this.items = items;
    }
    
    public void addItem(ItemGroup itemGroup) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(itemGroup);
    }

}
