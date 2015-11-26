package com.yidumen.cms.service;

import com.yidumen.cms.TagType;
import com.yidumen.cms.model.Tag;
import com.yidumen.cms.service.TagService;

import java.util.List;

public final class TagServiceImpl implements TagService {

    private final Tag dao;

    public TagServiceImpl() {
        dao = Tag.dao;
    }

    @Override
    public List<Tag> findColumnTags() {
        return dao.findByType(TagType.COLUMN);
    }

    @Override
    public List<Tag> findAll() {
        return dao.findAll();
    }

}
