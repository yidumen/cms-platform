package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Audio;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 蔡迪旻
 * 2015年10月21日.
 */
@Repository
@Transactional
public class AudioHibernateRepository<T> extends HibernateRepository<Audio> {
    public AudioHibernateRepository() {
        super(Audio.class);
    }

    public void create(final String title, final String file, final String HQFile) {
        final Audio entity = new Audio();
        entity.setTitle(title);
        entity.setFile(file);
        entity.setHQFile(HQFile);
        entity.setCreateDate(new Date());
        create(entity);
    }
}
