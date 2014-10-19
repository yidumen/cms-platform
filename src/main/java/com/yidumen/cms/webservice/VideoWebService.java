package com.yidumen.cms.webservice;

import com.yidumen.cms.service.impl.VideoServiceImpl;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoWebService {

    @Autowired
    private VideoServiceImpl service;

    public List<Video> allVideo() {
        return service.getVideos();
    }

    public Video findVideo(String file) {
        Video video = service.find(file);
        if (video == null) {
            video = new Video();
            video.setExtInfo(new ArrayList<VideoInfo>());
        }
        return video;
    }
    
    public void updateVideo(Video video) {
        service.updateVideo(video);
    }

}
