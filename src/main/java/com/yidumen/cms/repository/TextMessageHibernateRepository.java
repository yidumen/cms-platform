package com.yidumen.cms.repository;

import com.yidumen.cms.entity.TextMessage;
import org.springframework.stereotype.Repository;

/**
 * @author 蔡迪旻
 *         2015年11月30日
 */
@Repository
public class TextMessageHibernateRepository extends HibernateRepository<TextMessage> {
    public TextMessageHibernateRepository() {
        super(TextMessage.class);
    }
}
