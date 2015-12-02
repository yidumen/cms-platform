package com.yidumen.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yidumen.cms.VideoResolution;
import com.yidumen.cms.VideoResolutionDeserializer;
import com.yidumen.cms.VideoResolutionSerializer;

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
    private Video video;

    @JsonSerialize(using = VideoResolutionSerializer.class)
    @JsonDeserialize(using = VideoResolutionDeserializer.class)
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

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

}
