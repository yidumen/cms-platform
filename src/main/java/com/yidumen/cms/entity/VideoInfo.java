package com.yidumen.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.yidumen.cms.JacksonView;
import com.yidumen.cms.constant.VideoResolution;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 蔡迪旻<yidumen.com>
 */
@Entity
@Table(name = "resource_video_ext_info")
public class VideoInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "resolution")
    private VideoResolution resolution;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "file_size", length = 10)
    private String fileSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @JsonView(JacksonView.MostLess.class)
    public VideoResolution getResolution() {
        return resolution;
    }

    public void setResolution(VideoResolution resolution) {
        this.resolution = resolution;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonView(JacksonView.MostLess.class)
    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "id=" + id +
                ", resolution=" + resolution +
                ", width=" + width +
                ", height=" + height +
                ", fileSize='" + fileSize + '\'' +
                '}';
    }
}
