package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Tag;
import org.springframework.stereotype.Repository;

/**
 * @author 蔡迪旻
 *         2015年11月27日
 */
@Repository
public class TagHibernateRepository extends HibernateRepository<Tag> {
    public TagHibernateRepository() {
        super(Tag.class);
    }
}
