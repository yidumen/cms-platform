package com.yidumen.cms.view.converter;

import com.yidumen.dao.constant.VideoResolution;
import com.yidumen.dao.entity.VideoInfo;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author cdm
 */
@FacesConverter("extInfo")
public class ExtInfoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        String resolution = (String) uic.getAttributes().get("resolution");
        VideoInfo extInfo = new VideoInfo();
        extInfo.setResolution(VideoResolution.getResolutionByDescript(resolution));
        extInfo.setFileSize(Integer.parseInt(string));

        return extInfo;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof List) {
            String resolution = (String) uic.getAttributes().get("resolution");
            List<VideoInfo> infos = (List<VideoInfo>) o;
            for (VideoInfo info : infos) {
                if (info.getResolution().getDescript().equals(resolution)) {
                    return String.valueOf(info.getFileSize());
                }
            }
            return null;
        } else {
            final FacesMessage message = new FacesMessage();
            message.setSummary("解析错误");
            message.setDetail("参数类型不匹配");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);
        }

    }

}
