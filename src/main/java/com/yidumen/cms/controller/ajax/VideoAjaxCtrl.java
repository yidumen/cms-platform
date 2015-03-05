package com.yidumen.cms.controller.ajax;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yidumen.cms.constant.VideoStatus;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author 蔡迪旻
 */
@Before(Tx.class)
public final class VideoAjaxCtrl extends BaseAjaxCtrl {
    private final static Logger LOG = LoggerFactory.getLogger(VideoAjaxCtrl.class);
    private final VideoService service;

    public VideoAjaxCtrl() {
        service = ServiceFactory.generateVideoService();
    }

    public void info() {
        final List<Video> videos = service.getVideos();
        renderJson(videos);
    }

    public void detail() {
        final Long id = getParaToLong(0);
        final Video video = service.find(id).extInfo().addTags();
        renderJson(video);
    }

    public void manager() {
        final List<Video> videos = service.getVideos();
        for (Video video : videos) {
            video.addTags();
        }
        renderJson(videos);
    }

    public void publish() {
        Video video = new Video();
        video.set("status", VideoStatus.VERIFY.ordinal());
        renderJson(service.find(video));
    }

    public void pub() {
        Long id = getParaToLong(0);
        try {
            final Video video = service.publish(id);
            setAttr("code", 0);
            setAttr("message", "视频" + video.get("file") + "已发布！");
            renderJson();
        } catch (IOException | IllDataException | ParseException ex) {
            setAttr("code", 2);
            setAttr("message", ex.getLocalizedMessage());
            renderJson();
        }
    }

    public void update() {
        final boolean isUpdateDate = getParaToBoolean(0);
        final Video video = getModelFromJsonRequest(new Video());
        try {
            service.updateVideo(video, isUpdateDate);
            setAttr("code", 0);
            setAttr("message", "视频 " + video.get("file") + " 信息已成功更新");
            renderJson();
        } catch (IllDataException ex) {
            setAttr("code", 2);
            setAttr("message", ex.getLocalizedMessage());
            renderJson();
        }
    }

    public void create() {
        final Video video = getModelFromJsonRequest(new Video());
        try {
            service.addVideo(video);
            setAttr("code", 0);
            setAttr("message", "视频 " + video.get("file") + " 信息已添加");
            renderJson();
        } catch (IllDataException e) {
            setAttr("code", 2);
            setAttr("message", e.getLocalizedMessage());
            renderJson();
        }
    }

    public void max() {
        final String property = getPara(0);
        setAttr("max", service.findMax(property));
        renderJson();
    }
}
