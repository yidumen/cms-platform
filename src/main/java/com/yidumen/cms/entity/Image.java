package com.yidumen.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 蔡迪旻
 *         2015年12月02日
 */
@Entity
@Table(name = "resource_image")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image extends Resource {
    @Column(name = "file", length = 100)
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (file != null ? !file.equals(image.file) : image.file != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }
}
