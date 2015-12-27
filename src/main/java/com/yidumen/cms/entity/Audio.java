package com.yidumen.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 蔡迪旻
 * 2015年10月21日.
 */
@Entity
@Table(name = "resource_audio")
public class Audio extends Resource {
    @Column(name = "file", length = 64)
    private String file;
    @Column(name = "hq_file", length = 64)
    private String HQFile;

    public String getHQFile() {
        return HQFile;
    }

    public void setHQFile(String HQFile) {
        this.HQFile = HQFile;
    }

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
        if (!super.equals(o)) return false;

        Audio audio = (Audio) o;

        return !(getTitle() != null ? !getTitle().equals(audio.getTitle()) : audio.getTitle() != null) && !(getFile() != null ? !getFile().equals(audio.getFile()) : audio.getFile() != null) && !(getHQFile() != null ? !getHQFile().equals(audio.getHQFile()) : audio.getHQFile() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getFile() != null ? getFile().hashCode() : 0);
        result = 31 * result + (getHQFile() != null ? getHQFile().hashCode() : 0);
        return result;
    }
}
