package com.yidumen.cms.service;

import com.yidumen.cms.dao.Tag;
import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public interface TagService {

    List<Tag> findColumnTags();
}
