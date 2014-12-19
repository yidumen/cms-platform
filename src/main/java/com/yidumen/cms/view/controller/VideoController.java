package com.yidumen.cms.view.controller;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.OSSObject;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.model.VideoQueryModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Controller
@RequestMapping("video")
@SessionAttributes("query")
public final class VideoController {

    private final static Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService service;
    @Autowired
    private OSSClient client;

    @RequestMapping("manager")
    public String manager(Model model) {
        model.addAttribute("count", service.getVideoCount());
        model.addAttribute("query", new VideoQueryModel());
        return "video/video-manager";
    }

    @RequestMapping(value = "pagination/{index}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<Video> videoList(@CookieValue("page.size") int pageSize,
                                 @PathVariable("index") int index,
                                 @ModelAttribute("query") VideoQueryModel model) {
        model.setFirst((index - 1) * pageSize);
        model.setLimit(pageSize);
        return service.find(model);
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String editVideo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("video", service.find(id));
        return "video/video-edit";
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String submitVideo(@RequestBody Video video) {
        try {
            service.updateVideo(video);
        } catch (IllDataException ex) {
            return ex.getMessage();
        }
        return "0";
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public String queryVideo(Model model) {
        model.addAttribute("query", new VideoQueryModel());
        return "video/video-query";
    }

    @RequestMapping(value = "query/process", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String processQuery(@RequestBody VideoQueryModel query, Model model) {
        model.addAttribute("query", query);
        model.addAttribute("count", service.getVideoCount(query));
        return "video/video-manager";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String publishVideo(Model model) {
        model.addAttribute("video", new Video());
        return "video/video-create";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String createVideo(@ModelAttribute("video") Video video) {
        service.addVideo(video);
        return verify();
    }

    @RequestMapping(value = "publish", method = RequestMethod.GET)
    public String verify() {
        return "video/video-publish";
    }

    @RequestMapping(value = "verify", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Video> ajaxVerify(Model model) {
        final VideoQueryModel queryModel = new VideoQueryModel();
        queryModel.addStatus(VideoStatus.VERIFY);
        return service.find(queryModel);
    }

    @RequestMapping(value = "publish/{file}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> pulishConfim(@PathVariable("file") String file) {
        try {
            service.publish(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException | IllDataException | ParseException ex) {
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "bat/{id}")
    public void downloadBat(HttpServletResponse response, @PathVariable Long id) throws IOException {
        final Video video = service.find(id);
        OSSObject object;
        try {
            object = client.getObject("yidumen", "cms/cms_single.bat");
        } catch (OSSException | ClientException ex) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + video.getFile() + ".bat");
        final ServletOutputStream os;
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
                os.write(news.getBytes());
            }
        }
        os.close();
    }
}
