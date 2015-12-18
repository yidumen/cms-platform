package com.yidumen.cms.service;

import com.yidumen.cms.constant.TagType;
import com.yidumen.cms.entity.Tag;
import com.yidumen.cms.repository.TagHibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class TagService {

    @Autowired
    private TagHibernateRepository tagDao;

    @SuppressWarnings("unchecked")
    public List<Tag> findColumnTags() {
        final Tag model = new Tag();
        model.setType(TagType.COLUMN);
        return tagDao.find(model);
    }

    public List<Tag> findAll() {
        return tagDao.findAll();
    }

}
