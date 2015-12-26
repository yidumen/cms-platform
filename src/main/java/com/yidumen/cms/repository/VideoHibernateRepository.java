package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Video;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 蔡迪旻
 * 2015年10月21日.
 */
@Repository
public class VideoHibernateRepository extends HibernateRepository<Video> {
    @Autowired
    public VideoHibernateRepository(SessionFactory sessionFactory) {
        super(Video.class, sessionFactory);
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
        return getSessionFactory().getCurrentSession().getNamedQuery("video.newVideos").setMaxResults(limit).list();
    }

    public Long findSort() {
        return (Long) currentSession().getNamedQuery("video.chatroomSort").uniqueResult();
    }

}
