package com.yidumen.cms.service.impl;

import com.yidumen.cms.dao.Tag;
import com.yidumen.cms.dao.constant.TagType;
import com.yidumen.cms.service.TagService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final Tag dao;

    public TagServiceImpl() {
        dao = Tag.dao;
    }

    @Override
    public List<Tag> findColumnTags() {
        return dao.findByType(TagType.COLUMN);
    }

}
