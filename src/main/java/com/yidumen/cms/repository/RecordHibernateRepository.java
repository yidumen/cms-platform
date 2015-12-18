package com.yidumen.cms.repository;

import com.yidumen.cms.constant.MessageType;
import com.yidumen.cms.constant.RecordType;
import com.yidumen.cms.entity.Record;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年11月15日
 */
@Repository
public class RecordHibernateRepository extends HibernateRepository<Record> {
    @Autowired
    public RecordHibernateRepository(SessionFactory sessionFactory) {
        super(Record.class, sessionFactory);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Record> findNews() {
        final Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("msgType", MessageType.news));
        criteria.add(Restrictions.eq("recordType", RecordType.PLATFORM));
        return criteria.list();
    }
}
