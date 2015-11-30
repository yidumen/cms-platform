package com.yidumen.cms.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class HibernateRepository<T> {
    protected final Class<T> entityClass;
    @Autowired
    private SessionFactory sessionFactory;

    public HibernateRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Long create(T entity) {
        return (Long) getSessionFactory().getCurrentSession().save(entity);
    }

    @Transactional
    public void edit(T entity) {
        final Session currentSession = getSessionFactory().getCurrentSession();
        currentSession.clear();
        currentSession.saveOrUpdate(entity);
    }

    @Transactional
    public void remove(T entity) {
        getSessionFactory().getCurrentSession().delete(entity);
    }

    @Transactional
    public void remove(Long id) {
        final T entity = this.find(id);
        this.remove(entity);
    }

    @Transactional(readOnly = true)
    public T find(Long id) {
        return getSessionFactory().getCurrentSession().load(entityClass, id);
    }

    @Transactional(readOnly = true)
    public List find(T entity) {
        return getSessionFactory().getCurrentSession().createCriteria(entityClass).add(Example.create(entity)).list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public T findUnique(T entity) {
        return (T) getSessionFactory().getCurrentSession().createCriteria(entityClass).add(Example.create(entity)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return (List<T>) getSessionFactory().getCurrentSession().createCriteria(entityClass).list();
    }

    @Transactional(readOnly = true)
    public long count() {
        return (long) getSessionFactory().getCurrentSession().createCriteria(entityClass)
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findRange(int first, int size) {
        return (List<T>) getSessionFactory().getCurrentSession().createCriteria(entityClass)
                .setFirstResult(first)
                .setMaxResults(size)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findBetween(Map<String, Object[]> condition) {
        final Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
        for (String property : condition.keySet()) {
            final Object[] values = condition.get(property);
            criteria.add(Restrictions.between(property, values[0], values[1]));
        }
        return criteria.list();
    }

    public int max(String property) {
        return (int) getSessionFactory().getCurrentSession().createCriteria(entityClass).setProjection(Projections.max(property)).uniqueResult();
    }
}
