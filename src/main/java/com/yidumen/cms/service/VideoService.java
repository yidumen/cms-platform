package com.yidumen.cms.service;

import com.yidumen.cms.dao.Video;
import com.yidumen.cms.service.exception.IllDataException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface VideoService {

    Video find(Long id);

    Video find(String file);

    List<Video> getVideos();

    void removeVideo(Video video);

    void updateVideo(Video video, boolean updateDate) throws IllDataException;

    List<Video> find(Map<String, Object[]> condition);

    List<Video> find(Video video);

    long getVideoCount();

    int getVideoCount(Map<String, Object[]> condition);

    void addVideo(Video video);

    Video publish(Long id) throws IOException, IllDataException, ParseException;

    Object findMax(String property);
}
