package com.yidumen.cms.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yidumen.cms.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 蔡迪旻yidumen.com>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video extends Resource implements Serializable {

    private Long sort;

    /**
     * 视频文件编号
     */
    private String file;

    /**
     * 不同清晰度的视频信息
     */
    private Set<VideoInfo> extInfo;

    private Set<Tag> tags;

    private String description;
    private String note;
    private String grade;
    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    private Long duration;

    /**
     * 视频拍摄时间
     */
    private java.sql.Date shootTime;

    /**
     * 视频状态，可取的值：发布、审核、存档
     */
    @JsonSerialize(using = VideoStatusSerializer.class)
    @JsonDeserialize(using = VideoStatusDeSerializer.class)
    private VideoStatus status;

    private Integer recommend;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date pubDate;

    private List<VideoClipInfo> clipInfos;

    /**
     * 视频发布顺序号，在页面中显示时它紧跟在名称后面为用户提示视频的索引号
     *
     * @return 表示发布索引号的长整型数据
     */
    @JsonView(JacksonView.Normal.class)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @JsonView(JacksonView.Normal.class)
    public String getFile() {
        return file;
    }

    public void setFile(final String file) {
        this.file = file;
    }

    @JsonView(JacksonView.Normal.class)
    public VideoStatus getStatus() {
        return status;
    }

    public void setStatus(final VideoStatus status) {
        this.status = status;
    }

    @JsonView(JacksonView.Normal.class)
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @JsonView(JacksonView.Normal.class)
    public java.sql.Date getShootTime() {
        return shootTime;
    }

    public void setShootTime(java.sql.Date shootTime) {
        this.shootTime = shootTime;
    }

    @JsonView(JacksonView.WithAssociations.class)
    public Set<VideoInfo> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Set<VideoInfo> extInfo) {
        this.extInfo = extInfo;
    }

    @JsonView(JacksonView.WithAssociations.class)
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }


    /**
     * 视频发布日期.<br>
     * 这个属性的意义有两层：<br>
     * 1. 当发布日期在当前日期之后，说明这是计划发布的日期，系统将在指定时间自动进行发布工作；<br>
     * 2. 如果发布日期在当前日期之前，说明这是已发布（或以前发布过）的视频。
     *
     * @return 发布日期
     */
    @JsonView(JacksonView.Normal.class)
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    @JsonView(JacksonView.Normal.class)
    public String getDescription() {
        return description;
    }

    public void setDescription(String descrpition) {
        this.description = descrpition;
    }

    @JsonView(JacksonView.Normal.class)
    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    @JsonView(JacksonView.Normal.class)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JsonView(JacksonView.Normal.class)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<VideoClipInfo> getClipInfos() {
        return clipInfos;
    }

    public void setClipInfos(List<VideoClipInfo> clipInfos) {
        this.clipInfos = clipInfos;
    }

    public void addClipInfo(VideoClipInfo clipInfo) {
        if (this.clipInfos == null) {
            this.clipInfos = new ArrayList<>();
        }
        this.clipInfos.add(clipInfo);
    }
}
