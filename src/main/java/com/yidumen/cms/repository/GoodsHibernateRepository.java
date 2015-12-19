package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Goods;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 蔡迪旻 <yidumen.com>
 */
@Repository
public class GoodsHibernateRepository extends HibernateRepository<Goods> {
    @Autowired
    public GoodsHibernateRepository(SessionFactory sessionFactory) {
        super(Goods.class, sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<Goods> findValidate() {
        return getSessionFactory().getCurrentSession().getNamedQuery("Goods.findValidate").list();
    }

}
