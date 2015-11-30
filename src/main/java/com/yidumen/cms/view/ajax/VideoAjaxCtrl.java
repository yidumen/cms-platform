package com.yidumen.cms.view.ajax;

import com.yidumen.cms.VideoStatus;
import com.yidumen.cms.view.DWZResponse;
import com.yidumen.cms.view.DWZResponseBuilder;
import com.yidumen.cms.entity.Video;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 蔡迪旻
 */
@RestController
@RequestMapping("video")
public final class VideoAjaxCtrl {
    private final static Logger LOG = LoggerFactory.getLogger(VideoAjaxCtrl.class);
    @Autowired
    private VideoService service;

    @RequestMapping("info")
    public List<Video> info() {
        final List<Video> videos = service.getVideos();
        return videos;
    }

    @RequestMapping("detail/{id}")
    public Video detail(@PathVariable Long id) {
        final Video video = service.find(id);
        return video;
    }

    @RequestMapping("manager")
    public List<Video> manager() {
        final List<Video> videos = service.getVideos();
        return videos;
    }

    @RequestMapping("publish")
    public List<Video> publish() {
        Video video = new Video();
        video.setStatus(VideoStatus.VERIFY);
        return service.find(video);
    }

    @RequestMapping("pub/{id}")
    public DWZResponse pub(@PathVariable Long id,
                           @RequestParam Integer sort) {
        try {
            final Video video = service.publish(id, sort);
            return DWZResponseBuilder.initiate().success("视频" + video.getFile() + "已发布！").builder();
        } catch (IOException | IllDataException ex) {
            return DWZResponseBuilder.initiate().error(ex.getLocalizedMessage()).builder();
        }
    }

    @RequestMapping(value = "update/{isUpdateDate}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DWZResponse update(@PathVariable boolean isUpdateDate, @RequestBody Video video) {
        service.updateVideo(video, isUpdateDate);
        return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + " 信息已成功更新").forwardUrl("/video/manager").builder();
    }

    @RequestMapping(value = "updateAndVerify/{isUpdateDate}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DWZResponse updateAndVerify(@PathVariable boolean isUpdateDate, @RequestBody Video video) {
        service.updateAndVerify(video, isUpdateDate);
        return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + "  信息已更新并准备发布").forwardUrl("/video/publish").builder();
    }

    @RequestMapping(value = "updataAndArchive/{isUpdateDate}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DWZResponse updateAndArchive(@PathVariable boolean isUpdateDate, @RequestBody Video video) {
        service.updateAndArchive(video, isUpdateDate);
        return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + " 信息已更新并归档").forwardUrl("/video/manager").builder();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DWZResponse create(@RequestBody Video video) {
        try {
            service.addVideo(video);
            return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + " 信息已添加").forwardUrl("/video/publish").builder();
        } catch (IllDataException e) {
            return DWZResponseBuilder.initiate().error(e.getLocalizedMessage()).builder();
        }
    }

    @RequestMapping("max/{property}")
    public Map<String, Object> max(@PathVariable String property) {
        final Map<String, Object> result = new HashMap<>();
        result.put("max", service.findMax(property));
        return result;
    }

    @RequestMapping("sort")
    public Map<String, Object> sort() {
        final Map<String, Object> result = new HashMap<>();
        result.put("sort", service.getSort());
        return result;
    }

    @RequestMapping("clipInfo/{id}")
    public Video clipInfo(@PathVariable("id") Long videoId) {
        return service.find(videoId);
    }

    @RequestMapping("archive/{id}")
    public DWZResponse archive(@PathVariable("id") Long videoId) {
        final Video video = service.archive(videoId);
        return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + " 已归档").builder();
    }

    @RequestMapping("delete/{id}")
    public DWZResponse delete(@PathVariable("id") Long videoId) {
        service.delete(videoId);
        return DWZResponseBuilder.initiate().success("视频信息已删除。").builder();
    }
}
