package com.yidumen.cms.controller.ajax;

import com.jfinal.ext.test.ControllerTestCase;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.yidumen.cms.framework.JFinalTestConfig;
import com.yidumen.cms.model.Video;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class VideoAjaxCtrlTest extends ControllerTestCase<JFinalTestConfig> {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Test
    public void fixUrl() {
        final String url = "http://yimg.yidumen.com/yidumen";
        List<Record> sutras = Db.find("SELECT * FROM sutra");
        for (Record sutra : sutras) {
            final String content = sutra.getStr("content");
            if (content != null && content.contains(url)) {
                Db.update("sutra", sutra.set("content", content.replaceAll(url, "/resources")));
                LOG.info("{}", sutra.get("title"));
            }
        }
    }
    
    @Test
    public void testValidator() throws Exception {
        final Video video = new Video();
        video.set("sort", 0);
        doRequest(video);
        video.set("file", "A1001");
        doRequest(video);
        video.set("id", 884);
        doRequest(video);
        video.set("file", "A0001");
        doRequest(video);
        video.set("title", "测试验证器");
        doRequest(video);
        video.set("sort", 1);
        video.set("shootTime", new Date());
        doRequest(video);
    }

    
    
    @Test
    public void testQuery() throws Exception {
        LOG.info("sort : {}", use("/ajax/video/sort").invoke());
    }

    private void doRequest(Video video) {
        System.out.println("create: " + use("/ajax/video/create").post(JsonKit.toJson(video)).invoke());
//        System.out.println("update: " + use("/ajax/video/update").post(JsonKit.toJson(video)).invoke());
    }
}