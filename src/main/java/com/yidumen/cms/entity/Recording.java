package com.yidumen.cms.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.yidumen.cms.JacksonView;

import javax.persistence.*;
import java.util.List;

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
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "recording")
    private List<VideoClipInfo> clipInfos;

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public String getFile() {
        return file;
    }

    public void setFile(String name) {
        this.file = name;
    }

    @JsonView(value = {JacksonView.Less.class})
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<VideoClipInfo> getClipInfos() {
        return clipInfos;
    }

    public void setClipInfos(List<VideoClipInfo> clipInfos) {
        this.clipInfos = clipInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recording recording = (Recording) o;

        return !(id != null ? !id.equals(recording.id) : recording.id != null) && (file != null ? file.equals(recording.file) : recording.file == null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}
