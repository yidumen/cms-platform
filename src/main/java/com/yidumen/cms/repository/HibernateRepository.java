package com.yidumen.cms.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public class HibernateRepository<T> {
    protected final Class<T> entityClass;
    @Autowired
    private SessionFactory sessionFactory;
    public HibernateRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected SessionFactory getSessionFactory() {
        return  sessionFactory;
    }
    public void create(T entity) {
        getSessionFactory().getCurrentSession().persist(entity);
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

    @Transactional(readOnly = true)
    public T find(Long id) {
        return getSessionFactory().getCurrentSession().load(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return (List<T>)getSessionFactory().getCurrentSession().createCriteria(entityClass).list();
    }

    @Transactional(readOnly = true)
    public long count() {
        return (long) getSessionFactory().getCurrentSession().createCriteria(entityClass)
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findRange(int first, int size) {
        return (List<T>)getSessionFactory().getCurrentSession().createCriteria(entityClass)
                .setFirstResult(first)
                .setMaxResults(size)
                .list();
    }
}
