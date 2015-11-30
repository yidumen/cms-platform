package com.yidumen.cms.service;

import com.yidumen.cms.VideoStatus;
import com.yidumen.cms.entity.Record;
import com.yidumen.cms.entity.Recording;
import com.yidumen.cms.entity.Video;
import com.yidumen.cms.entity.VideoClipInfo;
import com.yidumen.cms.repository.VideoHibernateRepository;
import com.yidumen.cms.service.exception.IllDataException;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 蔡迪旻 <yidumen.com>
 */
@Service
public final class VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoService.class);
    @Autowired
    private VideoHibernateRepository videoDAO;
    private final String[] resolutions = {"180", "360", "480", "720"};

    public List<Video> getVideos() {
        return videoDAO.findAll();
    }

    public void updateVideo(final Video video, boolean updateDate) {
        if (updateDate) {
            video.setPubDate(new Date());
        }
        videoDAO.edit(video);
    }

    public Video find(final Long id) {
        return videoDAO.find(id);
    }

    public Video find(final String file) {
        final Video video = new Video();
        video.setFile(file);
        return videoDAO.findUnique(video);
    }

    public void removeVideo(final Video video) {
        videoDAO.remove(video);
    }

    public List<Video> find(final Map<String, Object[]> condition) {
        return videoDAO.findBetween(condition);
    }

    public long getVideoCount() {
        return videoDAO.count();
    }

    public int getVideoCount(final Map<String, Object[]> condition) {
        return videoDAO.findBetween(condition).size();
    }

    public void addVideo(final Video video) throws IllDataException {
        video.setDuration(0L);
        if (video.getSort() == null) {
            video.setSort(0L);
        }
        if (video.getRecommend() == null) {
            video.setRecommend(0);
        }
        video.setStatus(VideoStatus.VERIFY);
        videoDAO.edit(video);
    }

    public Video publish(final Long id, Integer sort) throws IOException, IllDataException {
        final Video video = videoDAO.find(id);
        boolean deployError = false;
        final StringBuilder errorMessage = new StringBuilder();
        for (String resolution : resolutions) {
            final String url = "http://v3.yidumen.com/video/" + resolution + "/" + video.getFile() + "_" + resolution + ".mp4";
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
        video.setPubDate(new Date());
        video.setStatus(VideoStatus.PUBLISH);
        if (sort != null) {
            video.setSort(sort.longValue());
        }
        videoDAO.edit(video);
        return video;
    }

    public Object findMax(String property) {
        return videoDAO.max(property);
    }

    public List<Video> getNewVideos(int limit) {
        return videoDAO.getNew(limit);
    }

    @SuppressWarnings("unchecked")
    public List<Video> find(Video video) {
        return videoDAO.find(video);
    }

    public Video findVideo(Video video) {
        return videoDAO.findUnique(video);
    }

    public int getSort() {
        return videoDAO.findSort();
    }

    public List<VideoClipInfo> findClip(Long videoId) {
        return videoDAO.find(videoId).getClipInfos();
    }

    public Video archive(Long videoId) {
        final Video video = videoDAO.find(videoId);
        video.setStatus(VideoStatus.ARCHIVE);
        videoDAO.edit(video);
        return video;
    }

    public void updateAndVerify(Video video, boolean isUpdateDate) {
        if (isUpdateDate) {
            video.setPubDate(new Date());
        }
        video.setStatus(VideoStatus.VERIFY);
        videoDAO.edit(video);
    }

    public void updateAndArchive(Video video, boolean isUpdateDate) {
        if (isUpdateDate) {
            video.setPubDate(new Date());
        }
        video.setStatus(VideoStatus.ARCHIVE);
        videoDAO.edit(video);
    }

    public void delete(Long videoid) {
        videoDAO.remove(videoid);
    }
}
