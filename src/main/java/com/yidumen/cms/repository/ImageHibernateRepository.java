package com.yidumen.cms.repository;

import com.yidumen.cms.entity.Image;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 蔡迪旻
 *         2015年10月19日.
 */
@Repository
@Transactional
public class ImageHibernateRepository extends HibernateRepository<Image> {
    @Autowired
    public ImageHibernateRepository(SessionFactory sessionFactory) {
        super(Image.class, sessionFactory);
    }

    public void create(String title, String file) {
        final Image entity = new Image();
        entity.setTitle(title);
        entity.setFile(file);
        entity.setCreateDate(new Date());
        create(entity);
    }

}
