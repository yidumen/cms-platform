package com.yidumen.cms.service;

import com.yidumen.cms.service.exception.IllDataException;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.model.VideoQueryModel;
import java.io.IOException;
import java.text.ParseException;
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

    void updateVideo(Video video) throws IllDataException;

    List<Video> find(VideoQueryModel model);

    long getVideoCount();

    Long getVideoCount(VideoQueryModel model);

    void addVideo(Video video);

    void publish(String file) throws IOException, IllDataException, ParseException;

    Long findMaxSortNumber();
}
