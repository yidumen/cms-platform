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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.BasicCredentialsProvider;
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

    private void validateSort(long sort, String file) throws IllDataException {
        VideoQueryModel model = new VideoQueryModel();
        model.setSort(sort);
        model.setSort2(sort);
        List<Video> videos = videoDAO.find(model);
        if (!videos.isEmpty()) {
            final String file2 = videos.get(0).getFile();
            if (!file.equals(file2)) {
                throw new IllDataException("发布序号已被 " + file + " 使用");
            }
        }
    }

    @Override
    public Long getVideoCount(VideoQueryModel model) {
        return videoDAO.count(model);
    }

    @Override
    public void addVideo(Video video) {
        video.setStatus(VideoStatus.VERIFY);
        videoDAO.create(video);
    }

    @Override
    public void publish(String file) throws IOException, IllDataException, ParseException {
        final HttpHost proxy = new HttpHost("10.242.175.127", 3128);
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        final AuthScope authScope = new AuthScope(proxy);
        credsProvider.setCredentials(authScope, new UsernamePasswordCredentials("1336663694481251_default_57", "rad2yu5i2s"));
        final Executor executor = Executor.newInstance()
                .auth(credsProvider.getCredentials(authScope));
        final HttpResponse response = executor.execute(
                Request.Get("http://mo01.yidumen.com/service/video/info/" + file).socketTimeout(5000).connectTimeout(5000)).returnResponse();
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 404) {
            throw new IllDataException("视频未部署");
        }
        if (statusCode == 500) {
            throw new IllDataException("请检查视频中转站是否在线");
        }
        final String json = EntityUtils.toString(response.getEntity());
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Object> map = mapper.readValue(json, Map.class);

        final Video video = videoDAO.find(file);
        video.setDuration(Long.valueOf(map.get("Duration").toString()));
        List<VideoInfo> videoInfos = video.getExtInfo();

        if (videoInfos == null || videoInfos.isEmpty()) {
            videoInfos = new ArrayList<>();
            for (VideoResolution resolution : VideoResolution.values()) {
                VideoInfo info = new VideoInfo();
                info.setResolution(resolution);
                info.setVideo(video);
                videoInfos.add(info);
            }
            video.setExtInfo(videoInfos);
        }
        log.debug("videoInfo has {} item", videoInfos.size());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        final List<Map<String, Object>> extInfo = (List<Map<String, Object>>) map.get("extInfo");
        log.debug("extInfo has {}", extInfo.size());
        for (Map<String, Object> infos : extInfo) {
            String info = infos.get("Resolution").toString();
            log.debug(info);
            for (VideoInfo videoInfo : videoInfos) {
                if (info.equals(videoInfo.getResolution().getResolution())) {
                    videoInfo.setHeight(Integer.parseInt(info));
                    videoInfo.setWidth(Integer.valueOf(infos.get("Width").toString()));
                    videoInfo.setFileSize(infos.get("FileSizeString").toString());
                    log.debug("Modified is {}", infos.get("Modified"));
                    video.setPubDate(format.parse(infos.get("Modified").toString()));
                }
            }
        }
        video.setStatus(VideoStatus.PUBLISH);
        videoDAO.edit(video);
    }

}
