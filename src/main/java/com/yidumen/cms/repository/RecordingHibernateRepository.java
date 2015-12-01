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

}
