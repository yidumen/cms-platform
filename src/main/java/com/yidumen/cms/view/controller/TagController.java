package com.yidumen.cms.view.controller;

import com.yidumen.cms.dao.Tag;
import com.yidumen.cms.service.TagService;
import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public class TagController {

    private TagService tagService;
    
    public List<Tag> columns() {
        return tagService.findColumnTags();
    }
}
