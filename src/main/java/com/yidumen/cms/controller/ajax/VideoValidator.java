package com.yidumen.cms.controller.ajax;

import com.jfinal.core.Controller;
import com.jfinal.ext.render.DwzRender;
import com.jfinal.validate.Validator;
import com.yidumen.cms.model.Video;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 蔡迪旻
 *         2015年03月11日
 */
public final class VideoValidator extends Validator {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final VideoService videoService;

    public VideoValidator() {
        videoService = ServiceFactory.generateVideoService();
    }

    @Override
    protected void validate(final Controller c) {
        if (c instanceof BaseAjaxCtrl) {
            final BaseAjaxCtrl baseAjaxCtrl = (BaseAjaxCtrl) c;
            final Video video = baseAjaxCtrl.getModelFromJsonRequest(new Video());
            if (video.get("file") == null) {
                addError("message", "视频编号 不能省略");
                return;
            } else {
                final Video validateVideo = videoService.find(video.getStr("file"));
                LOG.debug("validate video : {}", validateVideo);
                if (validateVideo != null) {
                    LOG.debug("id = {}->{}", video.get("id"), video.get("id") == null ? null : video.get("id").getClass().getName());
                    if (video.get("id") == null) {
                        addError("message", "视频编号已被占用");
                        return;
                    } else {
                        LOG.debug("id compare->{} = {}->{}", video.getNumber("id").longValue(), validateVideo.getNumber("id").longValue(), video.getNumber("id").longValue() != validateVideo.getNumber("id").longValue());
                        if (video.getNumber("id").longValue() != validateVideo.getNumber("id").longValue()) {
                            addError("message", "视频编号 " + video.get("file") + " 已被 " + validateVideo.get("title") + " 使用");
                            return;
                        }
                    }
                }
            }
            if (video.get("title") == null) {
                addError("message", "视频标题 不能省略");
                return;
            }
            if (video.get("shootTime") == null) {
                addError("message", "必须设置 拍摄日期");
                return;
            }
            if (video.get("sort") != null && Integer.parseInt(video.get("sort").toString()) > 0) {
                final Video model = new Video();
                model.set("sort", video.get("sort"));
                final Video validateVideo = videoService.findVideo(model);
                if (validateVideo != null && (video.get("id") == null || (video.getNumber("id").longValue() != validateVideo.getNumber("id").longValue()))) {
                    addError("message", "发布序号 " + video.get("sort") + " 已被编号为 " + validateVideo.get("file") + " 的视频使用");
                }
            }
            c.setAttr("video", video);
        }
    }

    @Override
    protected void handleError(Controller c) {
        c.render(DwzRender.error(c.getAttrForStr("message")));
    }
}
