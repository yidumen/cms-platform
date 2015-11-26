package com.yidumen.cms.repository;

import com.yidumen.cms.model.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Repository
public class GoodsHibernateRepository extends HibernateRepository<Goods>{

    public GoodsHibernateRepository() {
        super(Goods.class);
    }

    @SuppressWarnings("unchecked")
    public List<Goods> findValidate() {
        return getSessionFactory().getCurrentSession().getNamedQuery("findValidate").list();
    }
}
