package com.yidumen.cms.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    public Recording getRecording() {
        return recording;
    }

    public void setRecording(Recording recording) {
        this.recording = recording;
    }

    public Long getIn() {
        return in;
    }

    public void setIn(Long in) {
        this.in = in;
    }

    public Long getOut() {
        return out;
    }

    public void setOut(Long out) {
        this.out = out;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
