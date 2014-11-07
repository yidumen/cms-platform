package com.yidumen.cms.view.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public final class ItemGroup {

    private String name;
    private List<ItemModel> items;
    private int permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
    
    public void addItem(ItemModel item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

}
