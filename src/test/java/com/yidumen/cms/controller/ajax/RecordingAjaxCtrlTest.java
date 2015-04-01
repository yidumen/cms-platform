package com.yidumen.cms.controller.ajax;

import com.jfinal.ext.test.ControllerTestCase;
import com.jfinal.plugin.activerecord.Db;
import com.yidumen.cms.framework.JFinalTestConfig;
import com.yidumen.cms.service.RecordingService;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.VideoService;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class RecordingAjaxCtrlTest extends ControllerTestCase<JFinalTestConfig> {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final VideoService videoService = ServiceFactory.generateVideoService();
    private final RecordingService recordingService = ServiceFactory.generateRecordingService();

    @Test
    public void parseEDL() throws DocumentException {
        Db.update("DROP TABLE IF EXISTS `video_recording`;");
        Db.update("DROP TABLE IF EXISTS `recording`;");
        Db.update("CREATE TABLE IF NOT EXISTS `recording` (`id` BIGINT NOT NULL AUTO_INCREMENT, `file` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;");
        Db.update("CREATE TABLE IF NOT EXISTS `video_recording` (`id` BIGINT(20) NOT NULL,`video_id` BIGINT(20) NOT NULL,`recording_id` BIGINT(20) NOT NULL,`in` BIGINT(20) NULL,`out` BIGINT(20) NULL,`start` BIGINT(20) NULL,`end` BIGINT(20) NULL, INDEX `FK_video_id_idx` (`video_id` ASC), INDEX `FK_recording_id_idx` (`recording_id` ASC), PRIMARY KEY (`id`, `video_id`, `recording_id`),CONSTRAINT `FK_video_id` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,CONSTRAINT `FK_recording_id` FOREIGN KEY (`recording_id`) REFERENCES `recording` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE = InnoDB");

        final String path = "/Users/pholance/Downloads/video";
        final File[] parents = new File(path).listFiles();
        for (File parent : parents) {
            if (parent.isDirectory()) {
                for (File xml : parent.listFiles()) {
                    if (!xml.getName().endsWith("xml")) {
                        continue;
                    }
                    assertEquals(use("/ajax/recording/parseXML").post(xml).invoke(), "ok");
                }
            }
        }
    }
}