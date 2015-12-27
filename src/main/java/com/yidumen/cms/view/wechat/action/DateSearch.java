package com.yidumen.cms.view.wechat.action;

import com.yidumen.cms.constant.VideoStatus;
import com.yidumen.cms.entity.Video;
import com.yidumen.cms.service.VideoService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.TextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 蔡迪旻
 *         2015年12月24日
 */
public class DateSearch {
    @Autowired
    private VideoService service;
    private final static Logger LOGGER = LoggerFactory.getLogger(DateSearch.class);

    protected String parseWideChar(String content) {
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
        return String.valueOf(ch);
    }

    protected String parseChinese(String content) {
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
        return s.toString();
    }

    protected WxMpXmlOutMessage doSearch(String content, WxMpXmlMessage wxMessage) {
        final TextBuilder textBuilder = WxMpXmlOutMessage.TEXT().toUser(wxMessage.getFromUserName()).fromUser(wxMessage.getToUserName());
        final SimpleDateFormat fstr = new SimpleDateFormat("yyMMdd");

        final Calendar cstr = Calendar.getInstance();
        try {
            cstr.setTime(fstr.parse(content));
            LOGGER.debug("解析日期: {}", cstr.getTime());
        } catch (ParseException ex) {
            return textBuilder.content("请输入6位数字日期，如“140116”").build();
        }
        final StringBuilder restr = new StringBuilder();
        final Calendar ccur = Calendar.getInstance();
        if (cstr.after(ccur)) {
            LOGGER.debug("日期超前,返回最新视频");
            restr.append("最新视频（点击观看）:\n").append(getNewest());
            return textBuilder.content(restr.toString()).build();
        }
        try {
            ccur.setTime(fstr.parse("130718"));
            if (cstr.before(ccur)) {
                LOGGER.debug("日期太旧,返回最新视频");
                restr.append("最新视频（点击观看）:\n").append(getNewest());
                return textBuilder.content(restr.toString()).build();
            }
        } catch (ParseException ex) {
            return textBuilder.content("请输入6位数字日期，如“140116”").build();
        }
        final SimpleDateFormat outDate = new SimpleDateFormat("yyyy年MM月dd日");
        final List<Video> videos = getVideos(cstr);

        restr.append(outDate.format(cstr.getTime()));
        if (videos.isEmpty()) {
            LOGGER.debug("未找到视频.");
            restr.append("没有更新视频，最新视频是（点击观看）:")
                    .append(getNewest());
            return textBuilder.content(restr.toString()).build();
        } else {
            LOGGER.debug("查询到指定日期的视频,返回");
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
        final Date[] time = new Date[2];
        final Calendar start = Calendar.getInstance();
        start.setTime(calendar.getTime());
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        time[0] = start.getTime();
        final Calendar end = Calendar.getInstance();
        end.setTime(calendar.getTime());
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        time[1] = end.getTime();
        final Map<String, Object[]> condition = new HashMap<>();
        condition.put("pubDate", time);
        return service.findRange(condition).stream().filter(video -> video.getStatus() != VideoStatus.ARCHIVE).collect(Collectors.toList());
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

    protected String getContent(WxMpXmlMessage wxMessage) {
        switch (wxMessage.getMsgType()) {
            case WxConsts.XML_MSG_EVENT:
                return wxMessage.getEventKey();
            case WxConsts.XML_MSG_TEXT:
                return wxMessage.getContent();
            default:
                return null;
        }
    }
}
