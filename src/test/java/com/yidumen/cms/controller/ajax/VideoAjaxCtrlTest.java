package com.yidumen.cms.controller.ajax;

import com.jfinal.ext.test.ControllerTestCase;
import com.jfinal.kit.JsonKit;
import com.yidumen.cms.framework.JFinalTestConfig;
import com.yidumen.cms.model.Video;
import org.junit.Test;

import java.util.Date;

public class VideoAjaxCtrlTest extends ControllerTestCase<JFinalTestConfig> {
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

    private void doRequest(Video video) {
        System.out.println("create: " + use("/ajax/video/create").post(JsonKit.toJson(video)).invoke());
//        System.out.println("update: " + use("/ajax/video/update").post(JsonKit.toJson(video)).invoke());
    }
}