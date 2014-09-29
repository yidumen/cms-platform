package com.yidumen.cms.view.converter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@FacesConverter("sqlDate")
public final class SqlDateConverter implements Converter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        String pattern = (String) uic.getAttributes().get("pattern");
        if (pattern != null) {
            dateFormat = new SimpleDateFormat(pattern);
        }
        try {
            return new Date(dateFormat.parse(string).getTime());
        } catch (ParseException ex) {
            final FacesMessage message = new FacesMessage();
            message.setSummary("格式或pattern参数错误");
            message.setDetail("请检查格式" + dateFormat.toPattern()+"和参数"+string+"是否正确");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message, ex);
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof Date) {
            String pattern = (String) uic.getAttributes().get("pattern");
            if (pattern != null) {
                dateFormat = new SimpleDateFormat(pattern);
            }
            return dateFormat.format(o);
        } else {
            final FacesMessage message = new FacesMessage();
            message.setSummary("对象类型错误");
            message.setDetail("对象类型应该为java.sql.Date");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);

        }
    }

}
