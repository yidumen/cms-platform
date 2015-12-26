package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 蔡迪旻
 *         2015年11月27日
 */
@Repository
public class TagHibernateRepository extends HibernateRepository<Tag> {
    @Autowired
    public TagHibernateRepository(SessionFactory sessionFactory) {
        super(Tag.class, sessionFactory);
    }
}
