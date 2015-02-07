package com.yidumen.cms.view.controller.ajax;

import com.jfinal.core.Controller;
import com.jfinal.render.TextRender;
import com.yidumen.cms.dao.Video;
import com.yidumen.cms.dao.constant.VideoStatus;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.VideoService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.yidumen.cms.service.exception.IllDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻
 */
public final class VideoAjaxCtrl extends Controller {
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
            service.publish(id);
            setAttr("code", 0);
            renderJson();
        } catch (IOException | IllDataException | ParseException ex) {
            setAttr("code", 1);
            setAttr("message", ex.getLocalizedMessage());
            renderJson();
        }
    }
    
}
