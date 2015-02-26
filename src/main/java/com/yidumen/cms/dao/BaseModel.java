package com.yidumen.cms.dao;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;
import java.util.Map;

/**
 * @param <T>
 * @author 蔡迪旻
 * @version 1.0
 */
public class BaseModel<T extends Model> extends Model<T> {

    private final String table;

    public BaseModel(String table) {
        this.table = table;
    }

    /**
     * <p>
     * 指定查询条件，该方法使用SQL的<code>between</code>关键字查询。
     * <code>condition</code>中，key为字段名；value为装有2个对象的数组， 分别指定最大和最小值。</p>
     * <p>
     * 字符串也可以成为条件。日期也需要以字符串形式提供</p>
     *
     * @param condition
     * @return
     */
    public List<T> findBetween(Map<String, Object[]> condition) {
        StringBuilder sql = new StringBuilder("select * from ").append(table);
        if (condition == null) {
            return null;
        } else if (condition.isEmpty()) {
            return this.find(sql.toString());
        } else {
            sql.append(" where ");
            for (String attr : condition.keySet()) {
                sql.append(attr).append(" between ");
                Object[] objects = condition.get(attr);
                if (objects instanceof String[]) {
                    sql.append("'").append(objects[0]).append("' and '").append(objects[1]).append("'");
                } else {
                    sql.append(objects[0]).append(" and ").append(objects[1]);
                }
                sql.append(" and ");
            }
            sql.delete(sql.lastIndexOf(" and "), sql.length());
            return this.find(sql.toString());
        }
    }

    public List<T> findByCondition(T entity) {
        String[] attrNames = entity.getAttrNames();
        if (attrNames == null || attrNames.length == 0) {
            return null;
        }
        StringBuilder sql = new StringBuilder("select * from ").append(table).append(" where ");
        for (String attrName : attrNames) {
            sql.append(attrName).append(" = ?").append(" and ");
        }
        sql.delete(sql.lastIndexOf(" and "), sql.length());
        return this.find(sql.toString(), entity.getAttrValues());
    }

    public T findUnique(T entity) {
        String[] attrNames = entity.getAttrNames();
        if (attrNames == null || attrNames.length == 0) {
            return null;
        }
        StringBuilder sql = new StringBuilder("select * from ").append(table).append(" where ");
        for (String attrName : attrNames) {
            sql.append(attrName).append(" = ?").append(" and ");
        }
        sql.delete(sql.lastIndexOf(" and "), sql.length());
        return this.findFirst(sql.toString(), entity.getAttrValues());
    }

    public List<T> findAll() {
        return this.find("select * from " + table);
    }

    public Long count() {
        return this.findFirst("select count(id) as count from "+ table).getLong("count");
    }

    public Object max(String property) {
        return this.findFirst("select max(" + property + ") as max from " + table).get("max");
    }
}
