package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Video;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 蔡迪旻
 * 2015年10月21日.
 */
@Repository
@Transactional
public class VideoHibernateRepository extends HibernateRepository<Video> {
    public VideoHibernateRepository() {
        super(Video.class);
    }

    public void create(final String title, final String file) {
        final Video entity = new Video();
        entity.setTitle(title);
        entity.setFile(file);
        entity.setCreateDate(new Date());
        create(entity);
    }
}
