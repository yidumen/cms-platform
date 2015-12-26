package com.yidumen.cms.view.ajax;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.fasterxml.jackson.annotation.JsonView;
import com.yidumen.cms.JacksonView;
import com.yidumen.cms.constant.VideoStatus;
import com.yidumen.cms.entity.Video;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import com.yidumen.cms.view.DWZResponse;
import com.yidumen.cms.view.DWZResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 蔡迪旻
 */
@RestController("videoApi")
@RequestMapping("video")
public class VideoAjaxCtrl {
    private final static Logger LOG = LoggerFactory.getLogger(VideoAjaxCtrl.class);
    @Autowired
    private VideoService service;
    @Autowired
    private ApplicationContext context;

    @Transactional(readOnly = true)
    @RequestMapping("info")
    @JsonView(JacksonView.Less.class)
    public List<Video> info() {
        return service.getVideos();
    }

    @Transactional(readOnly = true)
    @RequestMapping("detail/{id}")
    @JsonView(JacksonView.More.class)
    public Video detail(@PathVariable Long id) {
        return service.find(id);
    }

    @Transactional(readOnly = true)
    @RequestMapping("manager")
    @JsonView(JacksonView.Normal.class)
    public List<Video> manager() {
        return service.getVideos();
    }

    @Transactional(readOnly = true)
    @RequestMapping("publish")
    @JsonView(JacksonView.Less.class)
    public List<Video> publish() {
        Video video = new Video();
        video.setStatus(VideoStatus.VERIFY);
        return service.find(video);
    }

    @Transactional
    @RequestMapping(value = "pub/{id}", method = RequestMethod.PUT)
    public DWZResponse pub(@PathVariable Long id,
                           @RequestParam Integer sort) {
        try {
            final Video video = service.publish(id, sort);
            return DWZResponseBuilder.initiate().success("视频" + video.getFile() + "已发布！").builder();
        } catch (IOException | IllDataException ex) {
            return DWZResponseBuilder.initiate().error(ex.getLocalizedMessage()).builder();
        }
    }

    @Transactional
    @RequestMapping(value = "update/{isUpdateDate}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DWZResponse update(@PathVariable boolean isUpdateDate, @RequestBody Video video) {
        service.updateVideo(video, isUpdateDate);
        return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + " 信息已成功更新").forwardUrl("/site/video/manager").builder();
    }

    @Transactional
    @RequestMapping(value = "updateAndVerify/{isUpdateDate}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DWZResponse updateAndVerify(@PathVariable boolean isUpdateDate, @RequestBody Video video) {
        try {
            service.updateAndVerify(video, isUpdateDate);
        } catch (IOException | IllDataException e) {
            return DWZResponseBuilder.initiate().error(e.getLocalizedMessage()).builder();
        }
        return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + "  信息已更新并准备发布").forwardUrl("/site/video/publish").builder();
    }

    @Transactional
    @RequestMapping(value = "updataAndArchive/{isUpdateDate}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DWZResponse updateAndArchive(@PathVariable boolean isUpdateDate, @RequestBody Video video) {
        service.updateAndArchive(video, isUpdateDate);
        return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + " 信息已更新并归档").forwardUrl("/site/video/manager").builder();
    }

    @Transactional
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DWZResponse create(@RequestBody Video video) {
        try {
            service.addVideo(video);
            return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + " 信息已添加").forwardUrl("/site/video/publish").builder();
        } catch (IllDataException e) {
            return DWZResponseBuilder.initiate().error(e.getLocalizedMessage()).builder();
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping("max/{property}")
    public Map<String, Object> max(@PathVariable String property) {
        final Map<String, Object> result = new HashMap<>();
        result.put("max", service.findMax(property));
        return result;
    }

    @Transactional(readOnly = true)
    @RequestMapping("sort")
    public Map<String, Object> sort() {
        final Map<String, Object> result = new HashMap<>();
        result.put("sort", service.getSort());
        return result;
    }

    @Transactional(readOnly = true)
    @RequestMapping("clipInfo/{id}")
    @JsonView(JacksonView.Special.class)
    public Video clipInfo(@PathVariable("id") Long videoId) {
        return service.find(videoId);
    }

    @Transactional
    @RequestMapping(value = "archive/{id}", method = RequestMethod.PUT)
    public DWZResponse archive(@PathVariable("id") Long videoId) {
        final Video video = service.archive(videoId);
        return DWZResponseBuilder.initiate().success("视频 " + video.getFile() + " 已归档").builder();
    }

    @Transactional
    @RequestMapping(value = "delete/{id}", method = RequestMethod.PUT)
    public DWZResponse delete(@PathVariable("id") Long videoId) {
        service.delete(videoId);
        return DWZResponseBuilder.initiate().success("视频信息已删除。").builder();
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "bat/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadBat(HttpServletResponse response, @PathVariable Long id) throws IOException {
        final Video video = service.find(id);
        final OSSClient client = context.getBean("ossClient", OSSClient.class);
        final String bucket = context.getBean("bucket", String.class);
        OSSObject object = client.getObject(bucket, "cms/cms_single.bat");
        response.setHeader("Content-Disposition", "attachment;filename=" + video.getFile() + ".bat");
        ServletOutputStream os = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(object.getObjectContent()))) {
            os = response.getOutputStream();
            String s;
            String[] shoottime = new SimpleDateFormat("yyyy,MM,dd").format(video.getShootTime()).split(",");
            while ((s = br.readLine()) != null) {
                String news;
                if (s.contains("filename_new")) {
                    news = s.replaceAll("filename_new", video.getFile() + "\r\n");
                } else if (s.contains("title_new")) {
                    news = s.replaceAll("title_new", video.getTitle() + "\r\n");
                } else if (s.contains("year_new")) {
                    news = s.replaceAll("year_new", shoottime[0].trim() + "\r\n");
                } else if (s.contains("month_new")) {
                    news = s.replaceAll("month_new", shoottime[1].trim() + "\r\n");
                } else if (s.contains("day_new")) {
                    news = s.replaceAll("day_new", shoottime[2].trim() + "\r\n");
                } else {
                    news = s + "\r\n";
                }
                os.write(news.getBytes("GBK"));
            }
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
}
