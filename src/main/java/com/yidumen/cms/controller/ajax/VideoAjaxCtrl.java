package com.yidumen.cms.controller.ajax;

import com.jfinal.aop.Before;
import com.jfinal.ext.render.DwzRender;
import com.jfinal.ext.render.excel.PoiRender;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yidumen.cms.VideoStatus;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
        final Integer sort = getParaToInt("sort");
        try {
            final Video video = service.publish(id, sort);
            render(DwzRender.success("视频" + video.get("file") + "已发布！"));
        } catch (IOException | IllDataException ex) {
            render(DwzRender.error(ex.getLocalizedMessage()));
        }
    }

    @Before(VideoValidator.class)
    public void update() {
        final boolean isUpdateDate = getParaToBoolean(0);
        final Video video = getAttr("video");
        service.updateVideo(video, isUpdateDate);
        render(DwzRender.success("视频 " + video.get("file") + " 信息已成功更新").forwardUrl("/video/manager"));
    }
    
    @Before(VideoValidator.class)
    public void updateAndVerify() {
        final boolean isUpdateDate = getParaToBoolean(0);
        final Video video = getAttr("video");
        service.updateAndVerify(video, isUpdateDate);
        render(DwzRender.success("视频 " + video.get("file") + " 信息已更新并准备发布").forwardUrl("/video/publish"));
    }

    @Before(VideoValidator.class)
    public void updateAndArchive() {
        final boolean isUpdateDate = getParaToBoolean(0);
        final Video video = getAttr("video");
        service.updateAndArchive(video, isUpdateDate);
        render(DwzRender.success("视频 " + video.get("file") + " 信息已更新并归档").forwardUrl("/video/manager"));
    }

    @Before(VideoValidator.class)
    public void create() {
        final Video video = getAttr("video");
        try {
            service.addVideo(video);
            render(DwzRender.success("视频 " + video.get("file") + " 信息已添加").forwardUrl("/video/publish"));
        } catch (IllDataException e) {
            render(DwzRender.error(e.getLocalizedMessage()));
        }
    }

    public void max() {
        final String property = getPara(0);
        setAttr("max", service.findMax(property));
        renderJson();
    }

    public void sort() {
        setAttr("sort", service.getSort());
        renderJson();
    }

    public void clipInfo() {
        final Long videoId = getParaToLong(0);
        setAttr("clips", service.findClip(videoId));
        setAttr("video", service.find(videoId));
        renderJson();
    }

    public void archive() {
        final Long videoId = getParaToLong(0);
        final Video video = service.archive(videoId);
        render(DwzRender.success("视频 " + video.get("file") + " 已归档"));
    }

    public void exportExcel() {
        final List<Video> videos = service.getVideosAndClips();
        final String[] header = {"编号", "标题", "剪辑来源"};
        render(PoiRender.me(videos)
                .fileName("clips.xlsx")
                .headers(header)
                .columns("file", "title", "clips")
                .sheetName("视频信息"));
    }
    
    public void delete() {
        final Long videoid = getParaToLong();
        service.delete(videoid);
        render(DwzRender.success("视频信息已删除。"));
    }
}
