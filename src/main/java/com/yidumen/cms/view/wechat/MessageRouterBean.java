package com.yidumen.cms.view.wechat;

import com.yidumen.cms.entity.ReplyKey;
import com.yidumen.cms.entity.ReplyMessage;
import com.yidumen.cms.service.WeChatService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 规则存储在数据库中,其设计是:关键字作为
 *
 * @author 蔡迪旻
 *         2015年11月29日
 */
@Configuration
public class MessageRouterBean {
    @Autowired
    private WeChatService service;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Bean(name = "wxRouter")
    @Autowired
    public WxMpMessageRouter generateRouter(WxMpService wxService) {
        final WxMpMessageRouter router = new WxMpMessageRouter(wxService);
        //设置消息路由,应该首先设置关注事件和关键字规则,最后是默认回复
        //1.取出所有的规则
        final List<ReplyKey> replyKeys = service.findReplies();
        for (ReplyKey key : replyKeys) {
            final ReplyMessage replyMessage = key.getMessage();
            final WxMpMessageHandler autoReplyhandler = new AutoReplyHandler(replyMessage);
            beanFactory.autowireBean(autoReplyhandler);
            //2.根据规则设置router
            switch (replyMessage.getType()) {
                case event:
                    switch (key.getKey()) {
                        case WxConsts.EVT_SUBSCRIBE:
                        case WxConsts.EVT_UNSUBSCRIBE:
                        case WxConsts.EVT_LOCATION:
                            router.rule()
                                    .msgType(WxConsts.XML_MSG_EVENT)
                                    .event(key.getKey())
                                    .handler(autoReplyhandler)
                                    .end();
                            break;
                        case WxConsts.BUTTON_CLICK:
                            router.rule()
                                    .msgType(WxConsts.XML_MSG_EVENT)
                                    .eventKey(key.getKey())
                                    .handler(autoReplyhandler)
                                    .end();
                    }
                    break;
                default:
                    switch (key.getType()) {
                        case CONTAINS:
                            router.rule().msgType(WxConsts.XML_MSG_TEXT).rContent(".*" + key.getKey() + ".*").handler(autoReplyhandler).end();
                            break;
                        case MATCHING:
                            router.rule().msgType(WxConsts.XML_MSG_TEXT).content(key.getKey()).handler(autoReplyhandler).end();
                            break;
                        case PATTEN:
                            router.rule().msgType(WxConsts.XML_MSG_TEXT).rContent(key.getKey()).handler(autoReplyhandler).end();
                    }
            }
        }
        final WxMpMessageHandler defaultHandler = new DefaultMessageHandler();
        beanFactory.autowireBean(defaultHandler);
        router.rule().handler(defaultHandler).end();

        return router;
    }
}
