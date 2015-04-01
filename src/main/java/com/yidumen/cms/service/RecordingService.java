package com.yidumen.cms.service;

import com.jfinal.plugin.activerecord.Record;
import com.yidumen.cms.model.Recording;
import org.dom4j.Document;

import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年03月12日
 */
public interface RecordingService {
    Recording find(String file);

    void parseXML(Document xml);

    List<Record> findClip(Long videoId);
}
