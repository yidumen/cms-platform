package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Fans;
import org.springframework.stereotype.Repository;

/**
 * @author 蔡迪旻
 *         2015年11月30日
 */
@Repository
public class FansHibernateRepository extends HibernateRepository<Fans> {
    public FansHibernateRepository() {
        super(Fans.class);
    }
}
