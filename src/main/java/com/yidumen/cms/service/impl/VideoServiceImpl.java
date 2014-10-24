package com.yidumen.cms.service.impl;

import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.SortInUseException;
import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.model.VideoQueryModel;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    @Autowired
    private VideoDAO videoDAO;

    @Override
    public List<Video> getVideos() {
        log.debug("获取全部视频");
        return videoDAO.findAll();
    }

    @Override
    public void updateVideo(final Video video) throws SortInUseException {
        if (video.getSort() > 0) {
            validateSort(video.getSort());
        }
        final Video update = videoDAO.find(video.getId());
        update.setTitle(video.getTitle());
        update.setSort(video.getSort());
        update.setDescrpition(video.getDescrpition());
        update.setNote(video.getNote());
        update.setChatroomVideo(video.isChatroomVideo());
        update.setShootTime(video.getShootTime());
        update.setRecommend(video.getRecommend());
        update.setGrade(video.getGrade());
        log.debug("更新Video {}", update.getTitle());
        videoDAO.edit(update);
    }

    @Override
    public Video find(Long id) {
        return videoDAO.find(id);
    }

    @Override
    public Video find(String file) {
        return videoDAO.find(file);
    }

    @Override
    public void removeVideo(Video video) {
        videoDAO.remove(video);
        log.debug("视频 {} 已删除", video.getTitle());
    }

    @Override
    public List<Video> find(VideoQueryModel model) {
        return videoDAO.find(model);
    }

    @Override
    public long getVideoCount() {
        return videoDAO.count();
    }

    private void validateSort(long sort) throws SortInUseException {
        VideoQueryModel model = new VideoQueryModel();
        model.setSort(sort);
        model.setSort2(sort);
        List<Video> videos = videoDAO.find(model);
        if (!videos.isEmpty()) {
            throw new SortInUseException("发布序号已被 " + videos.get(0).getFile() + " 使用");
        }
    }

    @Override
    public Long getVideoCount(VideoQueryModel model) {
        return videoDAO.count(model);
    }

}
