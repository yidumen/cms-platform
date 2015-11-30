package com.yidumen.cms.view.wechat.action;

import com.yidumen.cms.entity.Video;
import com.yidumen.cms.service.VideoService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @author 蔡迪旻 <yidumen.com>
 */
public final class GetNewestVideo implements WxMpMessageHandler {
    @Autowired
    private VideoService videoService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        List<Video> videos = videoService.getNewVideos(5);
        final StringBuilder restr = new StringBuilder("最新视频（点击观看）：\n");
        for (Video video : videos) {
            restr.append("\n<a href=\"")
                    .append("http://m.yidumen.com/video/")
                    .append(video.getFile())
                    .append("\">")
                    .append(video.getTitle())
                    .append("</a>\n");
        }
        restr.append("\n更多视频请点击：<a href=\"http://m.yidumen.com/video\">聊天室频道</a>");

        return WxMpXmlOutMessage
                .TEXT()
                .fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName())
                .content(restr.toString())
                .build();
    }
}
