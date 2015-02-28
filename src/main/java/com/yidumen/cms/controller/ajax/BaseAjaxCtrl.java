package com.yidumen.cms.controller.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cdm on 15/2/8.
 */
public class BaseAjaxCtrl extends Controller {
    protected final Logger LOG = Logger.getLogger(this.getClass());

    protected <T extends Model> T getModelFromJsonRequest(T model) {
        final Gson gson = new Gson();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final Map<String, Object> propertities = objectMapper.readValue(getRequest().getInputStream(), Map.class);
            for (String propertity : propertities.keySet()) {
                LOG.debug(propertity + " : " + propertities.get(propertity));
                if (propertity.equals("extInfo") || propertity.equals("tags")) {
                    final List<Map<String, Object>> os = (List<Map<String, Object>>) propertities.get(propertity);
                    final List<Record> records = new ArrayList<>();
                    for (Map<String, Object> o : os) {
                        records.add(new Record().setColumns(o));
                    }
                    model.put(propertity, records);
                } else {
                    model.set(propertity, propertities.get(propertity));
                }
            }
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
