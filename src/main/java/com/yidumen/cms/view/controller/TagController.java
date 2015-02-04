package com.yidumen.cms.view.controller;

import com.jfinal.aop.Before;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;
import com.yidumen.cms.dao.Tag;
import com.yidumen.cms.service.TagService;
import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
@Before(IocInterceptor.class)
public class TagController {
    @Inject.BY_TYPE
    private TagService tagService;
    
    public List<Tag> columns() {
        return tagService.findColumnTags();
    }
}
