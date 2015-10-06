/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yidumen.cms.controller.wechat.action;

import com.jfinal.weixin.sdk.msg.in.InMsg;
import com.jfinal.weixin.sdk.msg.out.OutMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.VideoService;

import java.util.List;

/**
 * @author 蔡迪旻 <yidumen.com>
 */
public final class GetNewestVideo implements Action {
    private final VideoService service;

    public GetNewestVideo() {
        service = ServiceFactory.generateVideoService();
    }

    @Override
    public OutMsg action(InMsg msg) {
        final OutTextMsg reply = new OutTextMsg(msg);
        List<Video> videos = service.getNewVideos(5);
        final StringBuilder restr = new StringBuilder("最新视频（点击观看）：\n");
        for (Video video : videos) {
            restr.append("\n<a href=\"")
                    .append("http://m.yidumen.com/video/")
                    .append(video.getStr("file"))
                    .append("\">")
                    .append(video.getStr("title"))
                    .append("</a>\n");
        }
        restr.append("\n更多视频请点击：<a href=\"http://m.yidumen.com/video\">聊天室频道</a>");

        reply.setContent(restr.toString());
        return reply;
    }
}
