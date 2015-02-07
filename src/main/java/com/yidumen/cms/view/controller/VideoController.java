package com.yidumen.cms.view.controller;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.render.TextRender;
import com.yidumen.cms.dao.Video;
import com.yidumen.cms.dao.constant.VideoStatus;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public final class VideoController extends Controller {

    private final static Logger LOG = LoggerFactory.getLogger(VideoController.class);
    private final VideoService service;
    private final OSSClient ossclient;

    public VideoController() {
        service = ServiceFactory.generateVideoService();
        PropKit.use("appengine-service.properties");
        ossclient = new OSSClient(PropKit.get("oss.ydm.endpoint"), PropKit.get("oss.ydm.accessKeyId"), PropKit.get("oss.ydm.accessKeySecret"));
    }

    public void info() {
        setSessionAttr("query", new HashMap<String, Object[]>());
        render("info.html");
    }

    @ActionKey("submit")
    public void submitVideo() {
        final Video video = getModel(Video.class);
        final Map<String, Object> result = new HashMap<>(2);
        try {
            service.updateVideo(video);
            setAttr("code", 0);
            setAttr("message", "视频信息已成功更新");
            renderJson();
        } catch (IllDataException ex) {
            setAttr("code", 1);
            setAttr("message", ex.getLocalizedMessage());
            renderJson();
        }
    }

    public void query() {
        setSessionAttr("query", new HashMap<String, Object[]>());
        render("query.html");
    }

    @ActionKey("query/process")
    public void processQuery() {
        Map<String, String[]> query = getParaMap();
        setSessionAttr("query", query);
        render("info.html");
    }

    public void create() {
        setAttr("video", new Video());
        render( "video/create");
    }

    public void add() {
        redirect("publish");
    }

    public void publish() {
        render("publish.html");
    }

    public void verift() {
        final Video video = new Video();
        video.set("status", VideoStatus.VERIFY.ordinal());
        setAttr("data", service.find(video));
        renderJson();
    }

    public void published() {
        final String file = getPara(0);
        try {
            service.publish(file);
            renderText("ok");
        } catch (IOException | IllDataException | ParseException ex) {
            renderError(500, new TextRender(ex.getLocalizedMessage()));
        }
    }

    public void max() {
        final String property = getPara(0);
        renderJson(service.findMax(property));
    }

    public void bat() throws IOException {
        final Long id = getParaToLong(0);
        final Video video = service.find(id);
        OSSObject object = ossclient.getObject("yidumen", "cms/cms_single.bat");
        HttpServletResponse response = getResponse();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + video.get("file") + ".bat");
        final ServletOutputStream os;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(object.getObjectContent()))) {
            os = response.getOutputStream();
            String s;
            String[] shoottime = new SimpleDateFormat("yyyy,MM,dd").format(video.getDate("shootTime")).split(",");
            while ((s = br.readLine()) != null) {
                String news;
                if (s.contains("filename_new")) {
                    news = s.replaceAll("filename_new", video.get("file") + "\r\n");
                } else if (s.contains("title_new")) {
                    news = s.replaceAll("title_new", video.get("title") + "\r\n");
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
        }
        os.close();
    }

    public void manager() {
        render("manager.html");
    }

    public void edit() {
        setAttr("video", service.find(getParaToLong(0)));
        render("edit.html");
    }
}
