package com.yidumen.cms.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.yidumen.cms.constant.VideoStatus;
import com.yidumen.cms.model.Recording;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.RecordingService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年03月12日
 */
public final class RecordingServiceImpl implements RecordingService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final Recording recordingDao;
    private final Video videoDao;

    public RecordingServiceImpl() {
        recordingDao = new Recording();
        videoDao = new Video();
    }

    @Override
    public Recording find(String file) {
        return recordingDao.findUnique(new Recording().set("file", file));
    }

    @Override
    public void parseXML(Document document) {
        final Element rootElement = document.getRootElement();
        final String title = rootElement.selectSingleNode("/xmeml/project/name").getText().substring(0, 5);
        final Video video = videoDao.findUnique(new Video().set("file", title));
        Object videoId;
        if (video == null) {
            new Video().set("file", title).set("title", "未命名").set("recommend", 0).set("duration", 0).set("shootTime", new Date()).set("sort", 0).set("status", VideoStatus.ARCHIVE.ordinal()).save();
            videoId = Db.findFirst("SELECT last_insert_id() AS id").get("id");
            LOG.info("不存在视频 {}，已创建视频", title);
        } else {
            videoId = video.get("id");
        }
        LOG.info("--------------------------------------------------------------");
        LOG.info("开始解析视频 {} 的源信息", title);
        LOG.info("--------------------------------------------------------------");
        //剪辑片断及特效都归在sequence元素下
        final Iterator<Element> clipitems = rootElement.selectNodes("//sequence/media/video/track[1]/*").iterator();
        long lastStart = 0;
        int i = 0;
        while (clipitems.hasNext()) {
            final Element clipitem = clipitems.next();
            switch (clipitem.getName()) {
                case "clipitem":
                    final Element file = clipitem.element("file");
                    //有的剪辑并非来自源视频，可能是一段特效片段，必须忽略。这种信息的特征是有一个mediaSource的子节点
                    if (rootElement.selectSingleNode("//file[@id='" + file.attributeValue("id") + "']/mediaSource") != null) {
                        break;
                    }
                    final String clipName = rootElement.selectSingleNode("//file[@id='" + file.attributeValue("id") + "']/name").getText();
                    //源视频都是AA+编号（以后可能会变化）
                    if (!clipName.startsWith("AA")) {
                        break;
                    }
                    final String recordingFile = clipName.substring(0, clipName.indexOf("."));
                    final Recording recording = recordingDao.findUnique(new Recording().set("file", recordingFile));
                    Object recordingId;
                    if (recording == null) {
                        new Recording().set("file", recordingFile).save();
                        recordingId = Db.findFirst("SELECT last_insert_id() AS id").get("id");
                        LOG.info("创建源视频信息 {}", recordingFile);
                    } else {
                        recordingId = recording.get("id");
                    }
                    final long in = Long.parseLong(clipitem.selectSingleNode("in").getText());
                    final long out = Long.parseLong(clipitem.selectSingleNode("out").getText());
                    long start = Long.parseLong(clipitem.selectSingleNode("start").getText());
                    if (start == -1) {
                        start = lastStart;
                    }
                    long end = Long.parseLong(clipitem.selectSingleNode("end").getText());
                    if (end == -1) {
                        end = start + out - in;
                    }
                    lastStart = end;

                    final Record clipInfo = new Record().set("id", i++).set("video_id", videoId).set("recording_id", recordingId).set("in", in).set("out", out).set("start", start).set("end", end);
//                    LOG.info("源视频{}第{}帧到{}帧 剪入第{}帧到{}帧", clipName, in, out, start, end);
                    LOG.debug(clipInfo.toString());
                    Db.save("video_recording", clipInfo);
                    break;
                case "transitionitem":
                    lastStart = Long.parseLong(clipitem.selectSingleNode("start").getText());
            }
        }
        i = 0;
    }

    @Override
    public List<Record> findClip(Long videoId) {
        return videoDao.findClips(videoId);
    }
}
