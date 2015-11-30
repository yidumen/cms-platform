package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Video;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    @SuppressWarnings("unchecked")
    public List<Video> getNew(int limit) {
        return getSessionFactory().getCurrentSession().getNamedQuery("com.yidumen.cms.entity.Video.newVideos").setMaxResults(limit).list();
    }

    public int findSort() {
        return (int) getSessionFactory().getCurrentSession().getNamedQuery("com.yidumen.cms.entity.Video.maxSort").uniqueResult();
    }
}
