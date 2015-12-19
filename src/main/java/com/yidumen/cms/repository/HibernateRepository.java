package com.yidumen.cms.repository;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class HibernateRepository<T> extends HibernateDaoSupport {
    protected final Class<T> entityClass;

    public HibernateRepository(Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.setSessionFactory(sessionFactory);
    }

    @Transactional
    public Long create(T entity) {
        return (Long) getHibernateTemplate().save(entity);
    }

    @Transactional
    public void edit(T entity) {
        final HibernateTemplate template = getHibernateTemplate();
        template.clear();
        template.saveOrUpdate(entity);
    }

    @Transactional
    public void remove(T entity) {
        getHibernateTemplate().delete(entity);
    }

    @Transactional
    public void remove(Long id) {
        final T entity = this.find(id);
        this.remove(entity);
    }

    @Transactional(readOnly = true)
    public T find(Long id) {
        final T entity = getHibernateTemplate().load(entityClass, id);
        return entity;
    }

    @Transactional(readOnly = true)
    public List<T> find(T entity) {
        return getHibernateTemplate().findByExample(entity);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public T findUnique(T entity) {
        return (T) currentSession().createCriteria(entityClass).add(Example.create(entity)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return (List<T>) currentSession().createCriteria(entityClass).list();
    }

    @Transactional(readOnly = true)
    public long count() {
        return (long) currentSession().createCriteria(entityClass)
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findRange(int first, int size) {
        return (List<T>) currentSession().createCriteria(entityClass)
                .setFirstResult(first)
                .setMaxResults(size)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findBetween(Map<String, Object[]> condition) {
        final Criteria criteria = currentSession().createCriteria(entityClass);
        for (String property : condition.keySet()) {
            final Object[] values = condition.get(property);
            criteria.add(Restrictions.between(property, values[0], values[1]));
        }
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public Long max(String property) {
        return Long.valueOf(currentSession().createCriteria(entityClass).setProjection(Projections.max(property)).uniqueResult().toString());
    }


}
