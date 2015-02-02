package com.yidumen.cms.view.controller;

import com.yidumen.cms.service.TagService;
import com.yidumen.dao.entity.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author 蔡迪旻
 */
@Controller
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;
    
    @RequestMapping(value = "columns", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tag> columnTags() {
        return tagService.findColumnTags();
    }
}
