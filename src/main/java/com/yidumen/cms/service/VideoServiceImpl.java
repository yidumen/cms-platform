package com.yidumen.cms.service;

import com.jfinal.plugin.activerecord.Record;
import com.yidumen.cms.VideoStatus;
import com.yidumen.cms.model.Recording;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.VideoService;
import com.yidumen.cms.service.exception.IllDataException;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 蔡迪旻 <yidumen.com>
 */
public final class VideoServiceImpl implements VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    private final Video videoDAO;
    private final String[] resolutions = {"180", "360", "480", "720"};

    public VideoServiceImpl() {
        this.videoDAO = Video.dao;
    }

    @Override
    public List<Video> getVideos() {
        return videoDAO.findAll();
    }

    @Override
    public void updateVideo(final Video video, boolean updateDate) {
        if (updateDate) {
            video.set("pubDate", new Date());
        }
        video.updateWithRelate();
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
        return videoDAO.count();
    }

    @Override
    public int getVideoCount(final Map<String, Object[]> condition) {
        return videoDAO.findBetween(condition).size();
    }

    @Override
    public void addVideo(final Video video) throws IllDataException {
        video.set("duration", 0);
        if (video.get("sort") == null) {
            video.set("sort", 0);
        }
        if (video.get("recommend") == null) {
            video.set("recommend", 0);
        }
        video.set("status", VideoStatus.VERIFY.ordinal());
        video.saveWithRelate();
    }

    @Override
    public Video publish(final Long id, Integer sort) throws IOException, IllDataException {
        final Video video = videoDAO.findById(id);
        boolean deployError = false;
        final StringBuilder errorMessage = new StringBuilder();
        for (String resolution : resolutions) {
            final String url = "http://v3.yidumen.com/video/" + resolution + "/" + video.getStr("file") + "_" + resolution + ".mp4";
            final Response response = Request.Head(url).connectTimeout(5000).execute();
            final HttpResponse httpResponse = response.returnResponse();
            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.debug("v3 response code: {}", statusCode);
            if (statusCode != 200) {
                if (!deployError) {
                    deployError = true;
                }
                errorMessage.append(resolution).append("p ");
            }
            response.discardContent();
        }
        if (deployError) {
            throw new IllDataException(errorMessage + "视频文件尚未部署，发布操作被拒绝！");
        }
        video.set("pubDate", new Date());
        video.set("status", VideoStatus.PUBLISH.ordinal());
        if (sort != null) {
            video.set("sort", sort);
        }
        video.updateWithRelate();
        return video;
    }

    @Override
    public Object findMax(String property) {
        return videoDAO.max(property);
    }

    @Override
    public List<Video> getNewVideos(int limit) {
        return videoDAO.getNew(limit);
    }

    @Override
    public List<Video> find(Video video) {
        return videoDAO.findByCondition(video);
    }

    @Override
    public Video findVideo(Video video) {
        return videoDAO.findUnique(video);
    }

    @Override
    public int getSort() {
        return videoDAO.findSort();
    }

    @Override
    public List<Record> findClip(Long videoId) {
        return videoDAO.findClips(videoId);
    }

    @Override
    public Video archive(Long videoId) {
        final Video video = videoDAO.findById(videoId);
        video.set("status", VideoStatus.ARCHIVE.ordinal()).update();
        return video;
    }

    @Override
    public List<Video> getVideosAndClips() {
        final List<Video> result = videoDAO.findAll();
        for (Video video : result) {
            final List<Recording> clips = videoDAO.findRecording(video.get("id"));
            final StringBuilder str = new StringBuilder();
            for (Recording clip : clips) {
                str.append(clip.getStr("file")).append(",");
            }
            if (str.length() > 0) {
                str.deleteCharAt(str.length() - 1);
                video.put("clips", str.toString());
            } else {
                video.put("clips", "未提供剪辑来源信息");
            }
        }
        return result;
    }

    @Override
    public void updateAndVerify(Video video, boolean isUpdateDate) {
        if (isUpdateDate) {
            video.set("pubDate", new Date());
        }
        video.set("status", VideoStatus.VERIFY.ordinal());
        video.updateWithRelate();
    }

    @Override
    public void updateAndArchive(Video video, boolean isUpdateDate) {
        if (isUpdateDate) {
            video.set("pubDate", new Date());
        }
        video.set("status", VideoStatus.ARCHIVE.ordinal());
        video.updateWithRelate();
    }

    @Override
    public void delete(Long videoid) {
        videoDAO.findById(videoid).deleteWithRelate();
    }
}
