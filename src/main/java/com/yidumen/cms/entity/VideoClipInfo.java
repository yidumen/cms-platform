package com.yidumen.cms.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年11月27日
 */
public class VideoClipInfo implements Serializable {
    private Long id;
    private Video video;
    private Recording recording;
    private Long in;
    private Long out;
    private Long start;
    private Long end;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
