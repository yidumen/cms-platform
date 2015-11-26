package com.yidumen.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by cdm on 2015/10/12.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image extends Resource {
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
