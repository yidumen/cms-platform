package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Audio;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 蔡迪旻
 * 2015年10月21日.
 */
@Repository
public class AudioHibernateRepository extends HibernateRepository<Audio> {
    @Autowired
    public AudioHibernateRepository(SessionFactory sessionFactory) {
        super(Audio.class, sessionFactory);
    }

    @Transactional
    public void create(final String title, final String file, final String HQFile) {
        final Audio entity = new Audio();
        entity.setTitle(title);
        entity.setFile(file);
        entity.setHQFile(HQFile);
        entity.setCreateDate(new Date());
        create(entity);
    }
}
