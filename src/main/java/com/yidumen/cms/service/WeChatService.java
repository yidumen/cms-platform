package com.yidumen.cms.service;

import com.yidumen.cms.constant.MessageType;
import com.yidumen.cms.entity.*;
import com.yidumen.cms.repository.FansHibernateRepository;
import com.yidumen.cms.repository.ReplyKeyHibernateRepository;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.NewsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    private final Logger logger;
    @Qualifier("baseUrl")
    @Autowired
    private String baseUrl;

    public WeChatService() {
        logger = LoggerFactory.getLogger(getClass());
    }

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
        model.setReplyType(MessageType.DEFAULT);
        return replyDao.findUnique(model);
    }

    @Transactional(readOnly = true)
    public WxMpXmlOutMessage parseMessage(AutowireCapableBeanFactory beanFactory,
                                          WxMpXmlMessage wxMessage,
                                          Map<String, Object> context,
                                          WxMpService wxMpService,
                                          WxSessionManager sessionManager,
                                          ReplyMessage platformMessage,
                                          String baseUrl) throws WxErrorException {
        WxMpXmlOutMessage result = null;
        final MessageType messageType = platformMessage.getType();
        logger.debug("消息回复类型:{}", messageType);
        switch (messageType) {
            case text: {
                final TextMessage message = (TextMessage) platformMessage.getMessage();
                result = WxMpXmlOutMessage
                        .TEXT()
                        .fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName())
                        .content(message.getContent())
                        .build();
            }
            break;
            case image: {
                final ImageMessage message = (ImageMessage) platformMessage.getMessage();
                result = WxMpXmlOutMessage
                        .IMAGE()
                        .fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName())
                        .mediaId(message.getMediaId())
                        .build();
            }
            break;
            case music: {
                final MusicMessage message = (MusicMessage) platformMessage.getMessage();
                final String directory = "music/";
                final String hqFile = message.getAudio().getHQFile();
                final String file = message.getAudio().getFile();
                result = WxMpXmlOutMessage
                        .MUSIC()
                        .fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName())
                        .title(message.getAudio().getTitle())
                        .description(message.getDescription())
                        .musicUrl(file.startsWith("http://") ? file : baseUrl + directory + file)
                        .hqMusicUrl(hqFile.startsWith("http://") ? hqFile : baseUrl + directory + "hq/" + hqFile)
                        .thumbMediaId(message.getThumbMediaId())
                        .build();
            }
            break;
            case voice: {
                final VoiceMessage message = (VoiceMessage) platformMessage.getMessage();
                result = WxMpXmlOutMessage
                        .VOICE()
                        .fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName())
                        .mediaId(message.getMediaId())
                        .build();
            }
            break;
            case news: {
                final NewsMessage message = (NewsMessage) platformMessage.getMessage();
                final NewsBuilder builder = WxMpXmlOutMessage
                        .NEWS()
                        .fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName());
                for (Aritcle aritcle : message.getAritcles()) {
                    final WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
                    item.setTitle(aritcle.getTitle());
                    item.setDescription(aritcle.getDescription());
                    item.setPicUrl(aritcle.getPicUrl().startsWith("http://") ? aritcle.getPicUrl() : baseUrl+"images/"+aritcle.getPicUrl());
                    item.setUrl(aritcle.getUrl());
                    builder.addArticle(item);
                }
                result = builder.build();
            }
            break;
            case action: {
                final ActionMessage message = (ActionMessage) platformMessage.getMessage();
                try {
                    final WxMpMessageHandler action = (WxMpMessageHandler) Class.forName(message.getClassname()).newInstance();
                    beanFactory.autowireBean(action);
                    result = action.handle(wxMessage, context, wxMpService, sessionManager);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
        return result;
    }
}
