/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yidumen.cms.controller.wechat.action;

import com.jfinal.weixin.sdk.msg.in.InMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.out.OutMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.VideoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SearchVideo implements Action {
    
    private final VideoService service;

    public SearchVideo() {
        service = ServiceFactory.generateVideoService();
    }

    @Override
    public OutMsg action(InMsg msg) {
        String content;
        if (msg instanceof InTextMsg) {
            final InTextMsg tmsg = (InTextMsg) msg;
            content = tmsg.getContent();
        } else {
            return null;
        }

        final String patten1 = "\\d{6}";
        final String patten2 = "[０１２３４５６７８９]{6}";
        final String patten3 = "[零一二两三四五六七八九]{6}";

        if (content.matches(patten1)) {
            return doSearch(content, msg);
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
            return doSearch(String.valueOf(ch), msg);
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
            return doSearch(s.toString(), msg);
        } else {
            return null;
        }
    }

    /**
     * @param content
     * @param msg
     * @return
     * @throws java.text.ParseException
     * @throws java.io.IOException
     */
    private OutMsg doSearch(String content, InMsg msg) {
        final OutTextMsg reply = new OutTextMsg(msg);

        final SimpleDateFormat fstr = new SimpleDateFormat("yyMMdd");

        final Calendar cstr = Calendar.getInstance();
        try {
            cstr.setTime(fstr.parse(content));
        } catch (ParseException ex) {
            reply.setContent("请输入6位数字日期，如“140116”");
            return reply;
        }
        final StringBuilder restr = new StringBuilder();
        final Calendar ccur = Calendar.getInstance();
        if (cstr.after(ccur)) {
            restr.append("最新视频（点击观看）:\n").append(getNewest());
            reply.setContent(restr.toString());
            return reply;
        }
        try {
            ccur.setTime(fstr.parse("130718"));
            if (cstr.before(ccur)) {
                restr.append("最新视频（点击观看）:\n").append(getNewest());
                reply.setContent(restr.toString());
                return reply;
            }
        } catch (ParseException ex) {
        }
        final SimpleDateFormat outDate = new SimpleDateFormat("yyyy年MM月dd日");
        final List<Video> videos = getVideos(cstr);

        restr.append(outDate.format(cstr.getTime()));
        if (videos.isEmpty()) {
            restr.append("没有更新视频，最新视频是（点击观看）:")
                    .append(getNewest());
            reply.setContent(restr.toString());
            return reply;
        } else {
            restr.append("的视频有（点击观看）：")
                    .append(getListVideoStr(videos));
            reply.setContent(restr.toString());
            return reply;
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
        time[0] = simpleDateFormat.format(calendar.getTime())+" 00:00:00";
        time[1] = simpleDateFormat.format(calendar.getTime())+" 23:59:59";
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
                    .append(video.getStr("file"))
                    .append("\">")
                    .append(video.getStr("title"))
                    .append("</a>\n");

        }

        return restr.toString();
    }
}
