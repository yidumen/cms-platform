package com.yidumen.cms.repository;

import com.yidumen.cms.GoodsStatus;
import com.yidumen.cms.entity.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 蔡迪旻 <yidumen.com>
 */
@Repository
public class GoodsHibernateRepository extends HibernateRepository<Goods> {

    public GoodsHibernateRepository() {
        super(Goods.class);
    }

    @SuppressWarnings("unchecked")
    public List<Goods> findValidate() {
        return getSessionFactory().getCurrentSession().getNamedQuery("com.yidumen.cms.entity.Goods.findValidate").list();
    }

}
