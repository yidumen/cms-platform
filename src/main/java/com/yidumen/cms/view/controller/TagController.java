package com.yidumen.cms.view.controller;

import com.yidumen.cms.dao.Tag;
import com.yidumen.cms.service.ServiceFactory;
import com.yidumen.cms.service.TagService;
import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public final class TagController {

    private final TagService tagService;

    public TagController() {
        tagService = ServiceFactory.generateTagService();
    }

    public List<Tag> columns() {
        return tagService.findColumnTags();
    }
}
