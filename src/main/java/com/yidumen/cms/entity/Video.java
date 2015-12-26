package com.yidumen.cms.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yidumen.cms.*;
import com.yidumen.cms.constant.VideoStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 蔡迪旻yidumen.com>
 */
@Entity
@Table(name = "resource_video")
@NamedQueries({
        @NamedQuery(name = "video.chatroomSort", query = "select max(v.sort) from Video v join v.tags as tag where tag.tagname='聊天室'"),
        @NamedQuery(name = "video.newVideos", query = "from Video v where v.status=com.yidumen.cms.constant.VideoStatus.PUBLISH order by v.pubDate desc")

})
public class Video extends Resource implements Serializable {

    @Column(name = "sort")
    private Long sort;

    /**
     * 视频文件编号
     */
    @Column(name = "file", length = 50)
    private String file;

    /**
     * 不同清晰度的视频信息
     */
    @OneToMany(mappedBy = "video")
    private List<VideoInfo> extInfo;

    @ManyToMany
    @JoinTable(name = "related_video_tag", joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;

    @Column(name = "description")
    private String description;
    @Column(name = "note")
    private String note;
    @Column(name = "grade", length = 5)
    private String grade;
    @Column(name = "duration")
    private Long duration;

    /**
     * 视频拍摄时间
     */
    @Column(name = "shoot_time")
    private java.sql.Date shootTime;

    /**
     * 视频状态，可取的值：发布、审核、存档
     */
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private VideoStatus status;

    @Column(name = "recommend")
    private Integer recommend;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @Column(name = "pub_date")
    private Date pubDate;

    @OneToMany(mappedBy = "video")
    private List<VideoClipInfo> clipInfos;

    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * 视频发布顺序号，在页面中显示时它紧跟在名称后面为用户提示视频的索引号
     *
     * @return 表示发布索引号的长整型数据
     */
    @JsonView(JacksonView.Less.class)
    public Long getSort() {
        return sort;
    }

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public String getFile() {
        return file;
    }

    public void setFile(final String file) {
        this.file = file;
    }

    @JsonView(JacksonView.Less.class)
    public VideoStatus getStatus() {
        return status;
    }

    public void setStatus(final VideoStatus status) {
        this.status = status;
    }

    @JsonView(JacksonView.Less.class)
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @JsonView(JacksonView.Less.class)
    public java.sql.Date getShootTime() {
        return shootTime;
    }

    public void setShootTime(java.sql.Date shootTime) {
        this.shootTime = shootTime;
    }

    @JsonView(JacksonView.More.class)
    public List<VideoInfo> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(List<VideoInfo> extInfo) {
        this.extInfo = extInfo;
    }

    @JsonView(JacksonView.Normal.class)
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
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
    @JsonView(JacksonView.Less.class)
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    @JsonView(JacksonView.More.class)
    public String getDescription() {
        return description;
    }

    public void setDescription(String descrpition) {
        this.description = descrpition;
    }

    @JsonView(JacksonView.Less.class)
    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    @JsonView(JacksonView.More.class)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JsonView(JacksonView.More.class)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @JsonView(value = {JacksonView.MuchMore.class, JacksonView.Special.class})
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
