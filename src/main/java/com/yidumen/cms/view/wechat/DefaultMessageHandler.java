package com.yidumen.cms.view.wechat;

import com.yidumen.cms.constant.EventType;
import com.yidumen.cms.constant.MessageType;
import com.yidumen.cms.constant.RecordType;
import com.yidumen.cms.entity.*;
import com.yidumen.cms.service.WeChatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.Date;
import java.util.Map;

/**
 * @author 蔡迪旻
 *         2015年11月30日
 */
public class DefaultMessageHandler implements WxMpMessageHandler {
    @Autowired
    private WeChatService service;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    private String baseUrl;

    @Override
    public WxMpXmlOutMessage handle(final WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        new Thread(() -> {
            final MessageType messageType = MessageType.valueOf(wxMessage.getMsgType());
            final Record record = new Record();
            record.setRecordType(RecordType.RECEIVE);
            record.setMsgType(messageType);
            final Fans fans = service.findFans(wxMessage.getFromUserName());
            record.setOwner(fans);
            record.setReaded(false);
            switch (messageType) {
                case text: {
                    final TextMessage message = new TextMessage();
                    message.setCreateTime(new Date(wxMessage.getCreateTime() * 1000));
                    message.setContent(wxMessage.getContent());
                    service.storeMessage(message, MessageType.text);
                }
                break;
                case image: {
                    final ImageMessage message = new ImageMessage();
                    message.setCreateTime(new Date(wxMessage.getCreateTime() * 1000));
                    message.setMediaId(wxMessage.getMediaId());
                    service.storeMessage(message, MessageType.image);
                }
                break;
                case location: {
                    final LocationMessage message = new LocationMessage();
                    message.setCreateTime(new Date(wxMessage.getCreateTime() * 1000));
                    message.setLabel(wxMessage.getLabel());
                    message.setLocationX(wxMessage.getLocationX());
                    message.setLocationY(wxMessage.getLocationY());
                    message.setScale(wxMessage.getScale());
                    service.storeMessage(message, MessageType.location);
                }
                break;
                case link: {
                    final LinkMessage message = new LinkMessage();
                    message.setCreateTime(new Date(wxMessage.getCreateTime() * 1000));
                    message.setTitle(wxMessage.getTitle());
                    message.setDescription(wxMessage.getDescription());
                    message.setUrl(wxMessage.getUrl());
                    service.storeMessage(message, MessageType.link);
                }
                break;
                case event: {
                    final EventMessage message = new EventMessage();
                    message.setCreateTime(new Date(wxMessage.getCreateTime() * 1000));
                    message.setEvent(EventType.valueOf(wxMessage.getEvent()));
                    message.setEventKey(wxMessage.getEventKey());
                    message.setCommonInfo(wxMessage.getTicket());
                    message.setPrecision(wxMessage.getPrecision());
                    service.storeMessage(message, MessageType.event);
                }
                break;
                case voice: {
                    final VoiceMessage message = new VoiceMessage();
                    message.setCreateTime(new Date(wxMessage.getCreateTime() * 1000));
                    message.setFormat(wxMessage.getFormat());
                    message.setMediaId(wxMessage.getMediaId());
                    service.storeMessage(message, MessageType.voice);
                }
            }
        }).start();
        final ReplyKey defaultReply = service.findDefaultReply();
        if (defaultReply == null) {
            return null;
        }
        return service.parseMessage(beanFactory, wxMessage, context, wxMpService, sessionManager, defaultReply.getMessage(), baseUrl);
    }
}
