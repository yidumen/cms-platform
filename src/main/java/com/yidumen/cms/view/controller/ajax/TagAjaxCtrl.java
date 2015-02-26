package com.yidumen.cms.view.controller.ajax;

import com.jfinal.core.Controller;
import com.yidumen.cms.dao.Tag;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.TagService;
import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public final class TagAjaxCtrl extends BaseAjaxCtrl {

    private final TagService tagService;

    public TagAjaxCtrl() {
        tagService = ServiceFactory.generateTagService();
    }

    public void columns() {
        renderJson(tagService.findColumnTags());
    }
    
    public void tags() {
        renderJson(tagService.findAll());
    }
}
