/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yidumen.cms.view.wechat.action;

import com.yidumen.cms.entity.Video;
import com.yidumen.cms.service.VideoService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.TextBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SearchVideo implements WxMpMessageHandler {

    @Autowired
    private VideoService service;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        String content;
        if (wxMessage.getMsgType().equals(WxConsts.XML_MSG_TEXT)) {
            content = wxMessage.getContent();
        } else {
            return null;
        }

        final String patten1 = "\\d{6}";
        final String patten2 = "[０１２３４５６７８９]{6}";
        final String patten3 = "[零一二两三四五六七八九]{6}";

        if (content.matches(patten1)) {
            return doSearch(content, wxMessage);
        } else if (content.matches(patten2)) {
            final char[] ch = content.toCharArray();
            for (char c : ch) {
                switch (c) {
                    case '０':
                        c = 0;
                        break;
                    case '１':
                        c = 1;
                        break;
                    case '２':
                        c = 2;
                        break;
                    case '３':
                        c = 3;
                        break;
                    case '４':
                        c = 4;
                        break;
                    case '５':
                        c = 5;
                        break;
                    case '６':
                        c = 6;
                        break;
                    case '７':
                        c = 7;
                        break;
                    case '８':
                        c = 8;
                        break;
                    case '９':
                        c = 9;
                        break;
                }
            }
            return doSearch(String.valueOf(ch), wxMessage);
        } else if (content.matches(patten3)) {
            final StringBuilder s = new StringBuilder();
            final char[] ch = content.toCharArray();
            for (char c : ch) {
                switch (c) {
                    case '零':
                        s.append(0);
                        break;
                    case '一':
                        s.append(1);
                        break;
                    case '二':
                    case '两':
                        s.append(2);
                        break;
                    case '三':
                        s.append(3);
                        break;
                    case '四':
                        s.append(4);
                        break;
                    case '五':
                        s.append(5);
                        break;
                    case '六':
                        s.append(6);
                        break;
                    case '七':
                        s.append(7);
                        break;
                    case '八':
                        s.append(8);
                        break;
                    case '九':
                        s.append(9);
                        break;
                }
            }
            return doSearch(s.toString(), wxMessage);
        } else {
            return null;
        }
    }

    private WxMpXmlOutMessage doSearch(String content, WxMpXmlMessage wxMessage) {
        final TextBuilder textBuilder = WxMpXmlOutMessage.TEXT().toUser(wxMessage.getFromUserName()).toUser(wxMessage.getToUserName());
        final SimpleDateFormat fstr = new SimpleDateFormat("yyMMdd");

        final Calendar cstr = Calendar.getInstance();
        try {
            cstr.setTime(fstr.parse(content));
        } catch (ParseException ex) {
            return textBuilder.content("请输入6位数字日期，如“140116”").build();
        }
        final StringBuilder restr = new StringBuilder();
        final Calendar ccur = Calendar.getInstance();
        if (cstr.after(ccur)) {
            restr.append("最新视频（点击观看）:\n").append(getNewest());
            return textBuilder.content(restr.toString()).build();
        }
        try {
            ccur.setTime(fstr.parse("130718"));
            if (cstr.before(ccur)) {
                restr.append("最新视频（点击观看）:\n").append(getNewest());
                return textBuilder.content(restr.toString()).build();
            }
        } catch (ParseException ex) {
        }
        final SimpleDateFormat outDate = new SimpleDateFormat("yyyy年MM月dd日");
        final List<Video> videos = getVideos(cstr);

        restr.append(outDate.format(cstr.getTime()));
        if (videos.isEmpty()) {
            restr.append("没有更新视频，最新视频是（点击观看）:")
                    .append(getNewest());
            return textBuilder.content(restr.toString()).build();
        } else {
            restr.append("的视频有（点击观看）：")
                    .append(getListVideoStr(videos));
            return textBuilder.content(restr.toString()).build();
        }
    }

    private String getNewest() {
        final SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        final List<Video> videos = service.getNewVideos(3);
        return getListVideoStr(videos);
    }

    private List<Video> getVideos(Calendar calendar) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String[] time = new String[2];
        time[0] = simpleDateFormat.format(calendar.getTime()) + " 00:00:00";
        time[1] = simpleDateFormat.format(calendar.getTime()) + " 23:59:59";
        final Map<String, Object[]> condition = new HashMap<>();
        condition.put("pubDate", time);
        final List<Video> videos = service.find(condition);
        return videos;
    }

    private String getListVideoStr(List<Video> videos) {
        final StringBuilder restr = new StringBuilder();
        for (Video video : videos) {
            restr.append("\n<a href=\"")
                    .append("http://m.yidumen.com/video/")
                    .append(video.getFile())
                    .append("\">")
                    .append(video.getTitle())
                    .append("</a>\n");

        }

        return restr.toString();
    }

}
