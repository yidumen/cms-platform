package com.yidumen.cms.repository;


import com.yidumen.cms.entity.Message;

/**
 * @author 蔡迪旻
 *         2015年11月15日
 */
public class MessageHibernateRepository extends HibernateRepository<Message> {
    public MessageHibernateRepository() {
        super(Message.class);
    }
}
