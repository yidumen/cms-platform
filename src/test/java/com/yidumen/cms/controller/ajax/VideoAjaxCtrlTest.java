package com.yidumen.cms.controller.ajax;

import com.jfinal.ext.test.ControllerTestCase;
import com.jfinal.kit.JsonKit;
import com.yidumen.cms.framework.JFinalTestConfig;
import com.yidumen.cms.model.Video;
import org.junit.Test;

public class VideoAjaxCtrlTest extends ControllerTestCase<JFinalTestConfig> {
    @Test
    public void testValidator() throws Exception {
        final Video video = new Video();
        video.set("duration", 0);
        final String rep = use("/ajax/video/create").post(JsonKit.toJson(video)).invoke();
        System.out.println(rep);

    }
}