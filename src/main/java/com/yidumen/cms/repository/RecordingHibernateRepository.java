package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Recording;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 蔡迪旻
 *         2015年11月27日
 */
@Repository
public class RecordingHibernateRepository extends HibernateRepository<Recording> {
    @Autowired
    public RecordingHibernateRepository(SessionFactory sessionFactory) {
        super(Recording.class, sessionFactory);
    }

}
