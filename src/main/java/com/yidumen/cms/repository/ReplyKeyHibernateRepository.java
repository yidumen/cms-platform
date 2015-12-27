package com.yidumen.cms.repository;

import com.yidumen.cms.entity.ReplyKey;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 蔡迪旻
 *         2015年10月29日
 */
@Repository
@Transactional
public class ReplyKeyHibernateRepository extends HibernateRepository<ReplyKey> {
    @Autowired
    public ReplyKeyHibernateRepository(SessionFactory sessionFactory) {
        super(ReplyKey.class, sessionFactory);
    }
}
