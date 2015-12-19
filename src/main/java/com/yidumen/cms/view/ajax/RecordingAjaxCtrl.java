package com.yidumen.cms.view.ajax;


import com.yidumen.cms.view.DWZResponse;
import com.yidumen.cms.view.DWZResponseBuilder;
import com.yidumen.cms.entity.Video;
import com.yidumen.cms.service.RecordingService;
import com.yidumen.cms.service.exception.IllDataException;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年03月12日
 */
@RestController
@RequestMapping("recording")
public class RecordingAjaxCtrl{

    @Autowired
    private RecordingService recordingService;

    @Transactional
    @RequestMapping(value = "parseXML", method = RequestMethod.POST)
    public DWZResponse parseXML(@RequestParam MultipartFile file) {
        try {
            final InputStream in = file.getInputStream();
            final StringBuilder xml = new StringBuilder();
            final List<String> lines = IOUtils.readLines(in, "utf-8");
            for (int i = 4; i < lines.size() - 2; i++) {
                xml.append(lines.get(i).trim());
            }
            final SAXReader reader = new SAXReader();
            final Document document = reader.read(IOUtils.toInputStream(xml));
            final Video video = recordingService.parseXML(document);
            return DWZResponseBuilder.initiate().success("XML 文件解析完成。").forwardUrl("/video/clipInfo/" + video.getId()).builder();
        } catch (IOException | DocumentException | IllDataException e) {
            return DWZResponseBuilder.initiate().error(e.getLocalizedMessage()).builder();
        }
    }
}
