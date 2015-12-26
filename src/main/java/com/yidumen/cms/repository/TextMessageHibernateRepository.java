package com.yidumen.cms.repository;

import com.yidumen.cms.entity.TextMessage;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 蔡迪旻
 *         2015年11月30日
 */
@Repository
public class TextMessageHibernateRepository extends HibernateRepository<TextMessage> {
    @Autowired
    public TextMessageHibernateRepository(SessionFactory sessionFactory) {
        super(TextMessage.class, sessionFactory);
    }
}
