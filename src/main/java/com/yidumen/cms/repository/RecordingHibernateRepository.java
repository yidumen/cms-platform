package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Recording;
import org.springframework.stereotype.Repository;

/**
 * @author 蔡迪旻
 *         2015年11月27日
 */
@Repository
public class RecordingHibernateRepository extends HibernateRepository<Recording> {
    public RecordingHibernateRepository() {
        super(Recording.class);
    }

    public Recording find(String file) {
        return (Recording) getSessionFactory().getCurrentSession().getNamedQuery("com.yidumen.cms.entity.Recording.findFile").setString("file", file).uniqueResult();
    }
}
