package com.yidumen.cms.service.impl;

import com.google.gson.Gson;
import com.jfinal.plugin.activerecord.Record;
import com.yidumen.cms.dao.Video;
import com.yidumen.cms.dao.constant.VideoResolution;
import com.yidumen.cms.dao.constant.VideoStatus;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoServiceImpl implements VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    private final Video videoDAO;

    public VideoServiceImpl() {
        this.videoDAO = Video.dao;
    }

    @Override
    public List<Video> getVideos() {
        return videoDAO.findAll();
    }

    @Override
    public void updateVideo(final Video video) throws IllDataException {
        if (video.getInt("sort") > 0) {
            validateSort(video.getInt("sort"), video.getStr("file"));
        }
        video.update();
    }

    @Override
    public Video find(final Long id) {
        return videoDAO.findById(id);
    }

    @Override
    public Video find(final String file) {
        final Video video = new Video();
        video.set("file", file);
        return video.findUnique(video);
    }

    @Override
    public void removeVideo(final Video video) {
        video.delete();
    }

    @Override
    public List<Video> find(final Map<String, Object[]> condition) {
        return videoDAO.findBetween(condition);
    }

    @Override
    public long getVideoCount() {
        return 0;
    }

    private void validateSort(final long sort, final String file) throws IllDataException {
        final Video model = new Video();
        model.set("sort", sort);
        final Video video = videoDAO.findUnique(model);
        if (video != null) {
            final String file2 = video.getStr("file");
            if (!file.equals(file2)) {
                throw new IllDataException("发布序号 " + sort + " 已被 " + file2 + " 使用");
            }
        }
    }

    @Override
    public int getVideoCount(final Map<String, Object[]> condition) {
        return videoDAO.findBetween(condition).size();
    }

    @Override
    public void addVideo(final Video video) {
        video.set("duration", 0);
        video.set("status", VideoStatus.VERIFY.ordinal());
        video.save();
    }

    @Override
    public void publish(final String file) throws IOException, IllDataException, ParseException {
        final HttpResponse response = Util.httpRequest("http://mo01.yidumen.com/service/video/info/" + file);
        final String json = EntityUtils.toString(response.getEntity());
        final Gson gson = new Gson();
        final Map<String, Object> map = gson.fromJson(json, Map.class);
        final Video model = new Video();
        model.set("file", file);
        final Video video = videoDAO.findUnique(model);
        video.set("duration",Long.valueOf(map.get("Duration").toString()));
        List<Record> videoInfos = video.extInfo().get("extInfo");
        if (videoInfos == null || videoInfos.isEmpty()) {
            videoInfos = new ArrayList<>();
            for (final VideoResolution resolution : VideoResolution.values()) {
                final Record info = new Record();
                info.set("resolution", resolution.ordinal());
                info.set("video_id", video.get("id"));
                videoInfos.add(info);
            }
            video.put("extInfo", videoInfos);
        }
        log.debug("videoInfo has {} item", videoInfos.size());
        final List<Map<String, Object>> extInfo = (List<Map<String, Object>>) map.get("extInfo");
        log.debug("extInfo has {}", extInfo.size());
        for (final Map<String, Object> infos : extInfo) {
            final String info = infos.get("Resolution").toString();
            log.debug(info);
            for (final Record videoInfo : videoInfos) {
                if (info.equals(VideoResolution.getByOrdinal(videoInfo.getInt("resolution")).getResolution())) {
                    videoInfo.set("height", Integer.parseInt(info));
                    videoInfo.set("width", Integer.parseInt(info));
                    videoInfo.set("fileSize", infos.get("FileSizeString").toString());
                }
            }
        }
        video.set("pubDate", new Date());
        video.set("status", VideoStatus.PUBLISH.ordinal());
        video.updateWithRelate();
    }

    @Override
    public Object findMax(String property) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Video> find(Video video) {
        return videoDAO.findByCondition(video);
    }


}
