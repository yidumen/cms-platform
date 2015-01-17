package com.yidumen.cms.view.model;

import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public class DataTableModel<T> {
    private List<T> data;

    public DataTableModel(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
    
}
