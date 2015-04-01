package com.yidumen.cms.controller.ajax;


import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yidumen.cms.service.RecordingService;
import com.yidumen.cms.service.ServiceFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.IOException;

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
            final SAXReader reader = new SAXReader();
            final Document document = reader.read(getRequest().getInputStream());
            recordingService.parseXML(document);
            renderText("ok");
        } catch (IOException | DocumentException e) {
            setAttr("code", 1);
            setAttr("message", e.getLocalizedMessage());
        }
    }
}
