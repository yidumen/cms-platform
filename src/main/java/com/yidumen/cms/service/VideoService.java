package com.yidumen.cms.service;

import com.jfinal.plugin.activerecord.Record;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.exception.IllDataException;

import java.io.IOException;
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

    void updateVideo(final Video video, boolean updateDate);

    List<Video> find(Map<String, Object[]> condition);

    List<Video> find(Video video);

    long getVideoCount();

    int getVideoCount(Map<String, Object[]> condition);

    void addVideo(Video video) throws IllDataException;

    Video publish(final Long id, Integer sort) throws IOException, IllDataException;

    Object findMax(String property);
    
    List<Video> getNewVideos(int limit);

    Video findVideo(Video video);

    int getSort();

    List<Record> findClip(Long videoId);

    Video archive(Long videoId);

    List<Video> getVideosAndClips();

    void updateAndVerify(Video video, boolean isUpdateDate);

    void updateAndArchive(Video video, boolean isUpdateDate);

    void delete(Long videoid);
}
