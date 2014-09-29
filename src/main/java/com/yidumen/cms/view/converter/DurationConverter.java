package com.yidumen.cms.view.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author 蔡迪旻
 */
@FacesConverter("duration")
public class DurationConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        String pattern = (String) uic.getAttributes().get("pattern");
        if (pattern == null) {
            pattern = "mm'ss\"";
        }
        StringBuilder sb = new StringBuilder(pattern);
        if (pattern.contains("h")) {
            sb.replace(sb.indexOf("h"), sb.lastIndexOf("h") + 1, "(?<hour>\\d*)");
        }
        if (pattern.contains("m")) {
            sb.replace(sb.indexOf("m"), sb.lastIndexOf("m") + 1, "(?<minute>\\d*)");
        }
        if (pattern.contains("s")) {
            sb.replace(sb.indexOf("s"), sb.lastIndexOf("s") + 1, "(?<second>\\d*)");
        }
        if (pattern.contains("S")) {
            sb.replace(sb.indexOf("S"), sb.lastIndexOf("S") + 1, "(?<mesc>\\d*)");
        }
        Matcher matcher = Pattern.compile(sb.toString()).matcher(string);
        if (matcher.matches()) {
            long mesc = 0;
            try {
                mesc = mesc + Long.parseLong(matcher.group("hour")) * 60 * 60 * 1000;
            } catch (IllegalArgumentException illegalArgumentException) {
                final FacesMessage message = new FacesMessage();
                message.setSummary("解析错误");
                message.setDetail("请检查值或pattern参数");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ConverterException(message);
            }
            try {
                mesc = mesc + Long.parseLong(matcher.group("minute")) * 60 * 1000;
            } catch (IllegalArgumentException illegalArgumentException) {
                final FacesMessage message = new FacesMessage();
                message.setSummary("解析错误");
                message.setDetail("请检查值或pattern参数");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ConverterException(message);
            }
            try {
                mesc = mesc + Long.parseLong(matcher.group("second")) * 1000;
            } catch (IllegalArgumentException illegalArgumentException) {
                final FacesMessage message = new FacesMessage();
                message.setSummary("解析错误");
                message.setDetail("请检查值或pattern参数");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ConverterException(message);
            }
            try {
                mesc = mesc + Long.parseLong(matcher.group("mesc"));
            } catch (IllegalArgumentException illegalArgumentException) {
                final FacesMessage message = new FacesMessage();
                message.setSummary("解析错误");
                message.setDetail("请检查值或pattern参数");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ConverterException(message);
            }
            return mesc;
        } else {
            final FacesMessage message = new FacesMessage();
            message.setSummary("解析错误");
            message.setDetail("对象与表达式不匹配，请检查值或pattern参数");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String pattern = (String) uic.getAttributes().get("pattern");
        if (pattern == null) {
            pattern = "mm'ss\"";
        }
        if (o instanceof Long) {
            Long time = (Long) o;
            StringBuilder sb = new StringBuilder(pattern);
            int hCount = 0, mCount = 0, sCount = 0, msCount = 0;
            if (pattern.contains("h")) {
                hCount = pattern.lastIndexOf("h") - pattern.indexOf("h") + 1;
            }
            if (pattern.contains("m")) {
                mCount = pattern.lastIndexOf("m") - pattern.indexOf("m") + 1;
            }
            if (pattern.contains("s")) {
                sCount = pattern.lastIndexOf("s") - pattern.indexOf("s") + 1;
            }
            if (pattern.contains("S")) {
                msCount = pattern.lastIndexOf("S") - pattern.indexOf("S") + 1;
            }
            long hour = 0, minute = 0, second = 0, mesc = 0;
            if (hCount > 0) {
                hour = time / 1000 / 60 / 60;
                sb.replace(sb.indexOf("h"), sb.lastIndexOf("h") + 1, String.format("%0" + hCount + "d", hour));
            }
            if (mCount > 0) {
                minute = time / (1000 * 60) - hour * 60;
                sb.replace(sb.indexOf("m"), sb.lastIndexOf("m") + 1, String.format("%0" + mCount + "d", minute));
            }
            if (sCount > 0) {
                second = time / 1000 - hour * 60 * 60 - minute * 60;
                sb.replace(sb.indexOf("s"), sb.lastIndexOf("s") + 1, String.format("%0" + sCount + "d", second));
            }
            if (msCount > 0) {
                mesc = time - hour * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                sb.replace(sb.indexOf("S"), sb.lastIndexOf("S") + 1, String.format("%0" + msCount + "d", mesc));
            }
            return sb.toString();
        } else {
            final FacesMessage message = new FacesMessage();
            message.setSummary("对象类型错误");
            message.setDetail("对象类型应该为Long");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);
        }
    }

}
