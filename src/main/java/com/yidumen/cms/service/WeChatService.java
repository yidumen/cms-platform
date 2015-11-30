package com.yidumen.cms.service;

import com.yidumen.cms.MessageType;
import com.yidumen.cms.entity.Fans;
import com.yidumen.cms.entity.Message;
import com.yidumen.cms.entity.ReplyKey;
import com.yidumen.cms.repository.FansHibernateRepository;
import com.yidumen.cms.repository.ReplyKeyHibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年11月29日
 */
@Service
public class WeChatService {
    @Autowired
    private ReplyKeyHibernateRepository replyDao;
    @Autowired
    private FansHibernateRepository fansDao;

    public List<ReplyKey> findReplies() {
        return replyDao.findAll();
    }

    public Fans findFans(String openId) {
        final Fans model = new Fans();
        model.setOpenId(openId);
        return fansDao.findUnique(model);
    }

    public void storeMessage(Message message, MessageType messageType) {

    }

    public ReplyKey findDefaultReply() {
        final ReplyKey model = new ReplyKey();
        model.setKey("default");
        return replyDao.findUnique(model);
    }
}
