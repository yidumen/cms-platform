package com.yidumen.cms.controller.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.yidumen.cms.model.Tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 蔡迪旻
 * 2015年03月05日
 */
public class BaseAjaxCtrl extends Controller {
    protected final Logger LOG = Logger.getLogger(this.getClass());
    @SuppressWarnings("unchecked")
    protected <T extends Model> T getModelFromJsonRequest(T model) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final Map<String, Object> propertities = objectMapper.readValue(getRequest().getInputStream(), Map.class);
            for (String propertity : propertities.keySet()) {
                LOG.debug(propertity + " : " + propertities.get(propertity));
                switch (propertity) {
                    case "extInfo": {
                        final List<Map<String, Object>> os = (List<Map<String, Object>>) propertities.get(propertity);
                        final List<Record> records = new ArrayList<>();
                        for (Map<String, Object> o : os) {
                            records.add(new Record().setColumns(o));
                        }
                        model.put(propertity, records);
                        break;
                    }
                    case "tags": {
                        final List<Map<String, Object>> os = (List<Map<String, Object>>) propertities.get(propertity);
                        final List<Tag> tags = new ArrayList<>();
                        for (Map<String, Object> o : os) {
                            tags.add(new Tag().setAttrs(o));
                        }
                        model.put(propertity, tags);
                        break;
                    }
                    default:
                        model.set(propertity, propertities.get(propertity));
                        break;
                }
            }
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
