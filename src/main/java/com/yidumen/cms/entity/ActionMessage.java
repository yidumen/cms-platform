package com.yidumen.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 蔡迪旻
 */
@Entity
@Table(name = "wechat_message_action")
public class ActionMessage extends Message {
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "class_name", length = 100)
    private String classname;
    @Column(name = "description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ActionMessage that = (ActionMessage) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getClassname() != null ? !getClassname().equals(that.getClassname()) : that.getClassname() != null)
            return false;
        return !(getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getClassname() != null ? getClassname().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

}
