package com.yidumen.cms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.constant.VideoResolution;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import com.yidumen.dao.model.VideoQueryModel;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
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
    public void updateVideo(final Video video) throws IllDataException {
        if (video.getSort() > 0) {
            validateSort(video.getSort(), video.getFile());
        }
        videoDAO.edit(video);
    }

    @Override
    public Video find(final Long id) {
        return videoDAO.find(id);
    }

    @Override
    public Video find(final String file) {
        return videoDAO.find(file);
    }

    @Override
    public void removeVideo(final Video video) {
        videoDAO.remove(video);
        log.debug("视频 {} 已删除", video.getTitle());
    }

    @Override
    public List<Video> find(final VideoQueryModel model) {
        model.setAllEager(true);
        return videoDAO.find(model);
    }

    @Override
    public long getVideoCount() {
        return videoDAO.count();
    }

    private void validateSort(final long sort, final String file) throws IllDataException {
        VideoQueryModel model = new VideoQueryModel();
        model.setSort(sort);
        model.setSort2(sort);
        List<Video> videos = videoDAO.find(model);
        if (!videos.isEmpty()) {
            final String file2 = videos.get(0).getFile();
            if (!file.equals(file2)) {
                throw new IllDataException("发布序号 " + sort + " 已被 " + file2 + " 使用");
            }
        }
    }

    @Override
    public Long getVideoCount(final VideoQueryModel model) {
        return videoDAO.count(model);
    }

    @Override
    public void addVideo(final Video video) {
        video.setDuration(0L);
        video.setStatus(VideoStatus.VERIFY);
        videoDAO.create(video);
    }

    @Override
    public void publish(final String file) throws IOException, IllDataException, ParseException {
        final CloseableHttpResponse response = Util.httpRequest("http://mo01.yidumen.com/service/video/info/" + file);
        final String json = EntityUtils.toString(response.getEntity());
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Object> map = mapper.readValue(json, Map.class);

        final Video video = videoDAO.find(file);
        video.setDuration(Long.valueOf(map.get("Duration").toString()));
        Set<VideoInfo> videoInfos = video.getExtInfo();

        if (videoInfos == null || videoInfos.isEmpty()) {
            videoInfos = new LinkedHashSet<>();
            for (final VideoResolution resolution : VideoResolution.values()) {
                final VideoInfo info = new VideoInfo();
                info.setResolution(resolution);
                info.setVideo(video);
                videoInfos.add(info);
            }
            video.setExtInfo(videoInfos);
        }
        log.debug("videoInfo has {} item", videoInfos.size());
        final List<Map<String, Object>> extInfo = (List<Map<String, Object>>) map.get("extInfo");
        log.debug("extInfo has {}", extInfo.size());
        for (final Map<String, Object> infos : extInfo) {
            final String info = infos.get("Resolution").toString();
            log.debug(info);
            for (final VideoInfo videoInfo : videoInfos) {
                if (info.equals(videoInfo.getResolution().getResolution())) {
                    videoInfo.setHeight(Integer.parseInt(info));
                    videoInfo.setWidth(Integer.valueOf(infos.get("Width").toString()));
                    videoInfo.setFileSize(infos.get("FileSizeString").toString());
                }
            }
        }
        video.setPubDate(new Date());
        video.setStatus(VideoStatus.PUBLISH);
        videoDAO.edit(video);
    }

    @Override
    public Object findMax(final String property) {
        final VideoQueryModel model = new VideoQueryModel();
        model.setMaxProperty(property);
        return videoDAO.max(model);
    }

}
