package com.yidumen.cms.service.impl;

import com.yidumen.cms.service.TagService;
import com.yidumen.dao.TagDAO;
import com.yidumen.dao.constant.TagType;
import com.yidumen.dao.entity.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDAO tagDAO;

    @Override
    public List<Tag> findColumnTags() {
        final Tag tag = new Tag();
        tag.setType(TagType.COLUMN);
        return tagDAO.find(tag, false);
    }

}
