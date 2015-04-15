package com.yidumen.cms.controller.ajax;


import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yidumen.cms.service.RecordingService;
import com.yidumen.cms.service.ServiceFactory;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年03月12日
 */
public final class RecordingAjaxCtrl extends BaseAjaxCtrl {
    
    private final RecordingService recordingService;

    public RecordingAjaxCtrl() {
        this.recordingService = ServiceFactory.generateRecordingService();
    }

    @Before(Tx.class)
    public void parseXML() {
        try {
            final ServletInputStream in = getRequest().getInputStream();
            final StringBuilder xml = new StringBuilder();
            final List<String> lines = IOUtils.readLines(in, "utf-8");
            for (int i = 4; i < lines.size() - 2; i++) {
                xml.append(lines.get(i).trim());
            }
            System.out.println(xml);
            final SAXReader reader = new SAXReader();
            final Document document = reader.read(IOUtils.toInputStream(xml));
            recordingService.parseXML(document);
            renderText("ok");
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            setAttr("code", 1);
            setAttr("message", e.getLocalizedMessage());
            renderJson();
        }
    }
}
