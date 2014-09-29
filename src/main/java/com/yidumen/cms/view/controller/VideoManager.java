package com.yidumen.cms.view.controller;

import com.yidumen.cms.service.VideoService;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import com.yidumen.dao.model.VideoQueryModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Named("video")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class VideoManager implements Serializable {

    private final static transient Logger log = LoggerFactory.getLogger(VideoManager.class);
    @Inject
    private transient VideoService service;

    private List<Video> videos;
    private Video video;
    private int count;
    private int curPage;
    private boolean editable;
    /**
     * 总页数
     */
    private int pageCount;
    private List<Integer> pages;

    private VideoQueryModel model;

    public VideoManager() {
        count = 12;
        curPage = 1;

        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("video.count");
        if (cookie != null) {
            count = Integer.parseInt(cookie.getValue());
            log.debug("检查到cookie，每页显示{}条记录", count);
        }

        model = new VideoQueryModel();
    }

    public void updateVideo() {
        log.debug("准备更新Video: {}", video.getTitle());
        service.updateVideo(video);
    }

    public void deleteVideo() {
        log.debug("准备删除视频 {}", video.getTitle());
        service.removeVideo(video);
    }

    public void refreshVideo(long id) {
        video = service.find(id);
        log.debug("Video 已经改变");
    }

    public List<Video> getVideos() {
        if (videos == null) {
            videos = service.getVideos();
            pageCount = videos.size() / count;
            if (videos.size() % count > 0) {
                pageCount++;
            }
            createPages();
            log.debug("总页数为{}，已创建{}个页码", pageCount, pages.size());
        }
        return videos;
    }

    private void createPages() {
        pages = new ArrayList();
        for (int i = 1; i <= pageCount; i++) {
            pages.add(i);
        }
    }

    public VideoStatus[] getVideoStatuses() {
        return VideoStatus.values();
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        log.debug("set video {}", video.getTitle());
        this.video = video;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        if (curPage > pageCount || curPage <= 0) {
            return;
        }
        log.debug("curPage={} will chang to {}", this.curPage, curPage);
        this.curPage = curPage;
    }

    /**
     * @return 每页显示行数*
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count 每页需要显示的行数*
     */
    public void setCount(int count) {
        this.count = count;
        pageCount = getVideos().size() / this.count;
        if (this.videos.size() % count > 0) {
            pageCount++;
        }
        createPages();
        if (curPage * count > pageCount) {
            curPage = pageCount;
        }
    }

    public String query() {
        videos = service.find(model);
        return "video-manager";
    }

    public List getPages() {
        return pages;
    }

    public int getPageCount() {
        return pageCount;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public VideoQueryModel getModel() {
        return model;
    }

    public void setModel(VideoQueryModel model) {
        this.model = model;
    }

    public void setExtInfo(VideoInfo info) {
        if (model.getExtInfo() == null) {
            model.setExtInfo(new ArrayList<VideoInfo>());
        }
        model.getExtInfo().add(info);
    }

    public void setExtInfo2(VideoInfo info) {
        if (model.getExtInfo2() == null) {
            model.setExtInfo2(new ArrayList<VideoInfo>());
        }
        model.getExtInfo2().add(info);
    }

    public List<VideoInfo> getExtInfo() {
        if (model.getExtInfo() == null) {
            model.setExtInfo(new ArrayList<VideoInfo>());
        }
        return model.getExtInfo();
    }

    public List<VideoInfo> getExtInfo2() {
        if (model.getExtInfo2() == null) {
            model.setExtInfo2(new ArrayList<VideoInfo>());
        }
        return model.getExtInfo2();
    }
}
