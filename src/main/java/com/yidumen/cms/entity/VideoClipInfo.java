package com.yidumen.cms.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.yidumen.cms.JacksonView;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 蔡迪旻
 *         2015年11月27日
 */
@Entity
@Table(name = "resource_video_clip_info")
public class VideoClipInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;
    @ManyToOne
    @JoinColumn(name = "recording_id")
    private Recording recording;
    @Column(name = "in_time")
    private Long in;
    @Column(name = "out_time")
    private Long out;
    @Column(name = "start_time")
    private Long start;
    @Column(name = "end_time")
    private Long end;

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(value = {JacksonView.MostLess.class})
    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public Recording getRecording() {
        return recording;
    }

    public void setRecording(Recording recording) {
        this.recording = recording;
    }

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public Long getIn() {
        return in;
    }

    public void setIn(Long in) {
        this.in = in;
    }

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public Long getOut() {
        return out;
    }

    public void setOut(Long out) {
        this.out = out;
    }

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    @JsonView(value = {JacksonView.MostLess.class, JacksonView.Special.class})
    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "VideoClipInfo{" +
                "id=" + id +
                ", in=" + in +
                ", out=" + out +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
