package com.yidumen.cms.service;

import com.jfinal.plugin.activerecord.Record;
import com.yidumen.cms.model.Recording;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.exception.IllDataException;
import org.dom4j.Document;

import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年03月12日
 */
public interface RecordingService {
    Recording find(String file);

    Video parseXML(Document document) throws IllDataException;

    List<Record> findClip(Long videoId);
}
