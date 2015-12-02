package com.yidumen.cms.entity;

import javax.persistence.*;

/**
 * @author 蔡迪旻
 *         2015年11月27日
 */
@Entity
@Table(name = "cms_recording")
public class Recording {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "file_name")
    private String file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String name) {
        this.file = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recording recording = (Recording) o;

        if (id != null ? !id.equals(recording.id) : recording.id != null) return false;
        return file != null ? file.equals(recording.file) : recording.file == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}
