package com.yidumen.cms.service;

import com.yidumen.cms.service.exception.SortInUseException;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.model.VideoQueryModel;
import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface VideoService {

    Video find(Long id);

    Video find(String file);

    List<Video> getVideos();

    void removeVideo(Video video);

    void updateVideo(Video video) throws SortInUseException;

    List<Video> find(VideoQueryModel model);

    long getVideoCount();

    Long getVideoCount(VideoQueryModel model);
}
