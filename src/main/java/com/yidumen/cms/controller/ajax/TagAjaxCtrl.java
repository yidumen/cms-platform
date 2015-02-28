package com.yidumen.cms.controller.ajax;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.TagService;

/**
 *
 * @author 蔡迪旻
 */
@Before(Tx.class)
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
