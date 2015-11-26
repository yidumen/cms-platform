package com.yidumen.cms.entity;

/**
 * @author 蔡迪旻
 * 2015年11月15日
 */
public class Video extends Resource {
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

        Video video = (Video) o;

        if (file != null ? !file.equals(video.file) : video.file != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }
}
