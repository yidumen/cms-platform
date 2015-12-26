package com.yidumen.cms.service;

import com.yidumen.cms.constant.VideoStatus;
import com.yidumen.cms.entity.*;
import com.yidumen.cms.repository.RecordingHibernateRepository;
import com.yidumen.cms.repository.TagHibernateRepository;
import com.yidumen.cms.repository.VideoHibernateRepository;
import com.yidumen.cms.service.exception.IllDataException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 蔡迪旻
 *         2015年03月12日
 */
@Service
public final class RecordingService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecordingHibernateRepository recordingDao;
    @Autowired
    private VideoHibernateRepository videoDao;
    @Autowired
    private TagHibernateRepository tagDao;

    public Recording find(String file) {
        final Recording model=new Recording();
        model.setFile(file);
        return recordingDao.findUnique(model);
    }

    @SuppressWarnings("unchecked")
    public Video parseXML(Document document) throws IllDataException {
        final Tag condition1 = new Tag();
        condition1.setTagname("聊天室");
        final Tag tag = tagDao.findUnique(condition1);
        final Element rootElement = document.getRootElement();
        final String title = rootElement.selectSingleNode("/xmeml/project/name").getText().substring(0, 5);
        if (title.isEmpty()) {
            throw new IllDataException("XML 文件格式不正确，请上传Final Cut Pro项目文件");
        }
        final Video condition2 = new Video();
        condition2.setFile(title);
        Video video = videoDao.findUnique(condition2);
        Object videoId = null;
        if (video == null) {
            video = new Video();
            video.setFile(title);
            video.setRecommend(0);
            video.setDuration(0L);
            video.setShootTime(new java.sql.Date(System.currentTimeMillis()));
            video.setSort(0L);
            video.setStatus(VideoStatus.ARCHIVE);
            final List<Tag> tags = new ArrayList<>();
            tags.add(tag);
            video.setTags(tags);
            LOG.info("不存在视频 {}，已创建视频", title);
        }
        LOG.info("--------------------------------------------------------------");
        LOG.info("开始解析视频 {} 的源信息", title);
        LOG.info("--------------------------------------------------------------");
        //剪辑片断及特效都归在sequence元素下
        final Iterator<Element> clipitems = rootElement.selectNodes("//sequence/media/video/track[1]/*").iterator();
        long lastStart = 0;
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
                    final Recording condition3 = new Recording();
                    condition3.setFile(recordingFile);
                    Recording recording = recordingDao.findUnique(condition3);
                    Object recordingId;
                    final VideoClipInfo clipInfo = new VideoClipInfo();
                    clipInfo.setVideo(video);
                    if (recording == null) {
                        clipInfo.setRecording(condition3);
                        LOG.info("创建源视频信息 {}", recordingFile);
                    } else {
                        clipInfo.setRecording(recording);
                    }
                    video.addClipInfo(clipInfo);
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
                    clipInfo.setIn(in);
                    clipInfo.setOut(out);
                    clipInfo.setStart(start);
                    clipInfo.setEnd(end);
//                    LOG.info("源视频{}第{}帧到{}帧 剪入第{}帧到{}帧", clipName, in, out, start, end);
                    LOG.debug(clipInfo.toString());
                    videoDao.edit(video);
                    break;
                case "transitionitem":
                    lastStart = Long.parseLong(clipitem.selectSingleNode("start").getText());
            }
        }
        return video;
    }

}
