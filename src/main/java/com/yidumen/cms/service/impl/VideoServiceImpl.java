package com.yidumen.cms.service.impl;

import com.yidumen.cms.service.VideoService;
import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.model.VideoQueryModel;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void updateVideo(Video video) {
        log.debug("更新Video {}", video.getTitle());
        videoDAO.edit(video);
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
    public List<Video> nextPage(int currentPage, int size) {
        return videoDAO.findRange((currentPage + 1) * size + 1, size);
    }

}
