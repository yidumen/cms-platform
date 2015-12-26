package com.yidumen.cms.view.wechat;

import com.yidumen.cms.entity.ReplyKey;
import com.yidumen.cms.entity.ReplyMessage;
import com.yidumen.cms.service.WeChatService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(getClass());

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
            switch (key.getReplyType()) {
                case event:
                    logger.debug("匹配事件: {}", key.getKey());
                    router.rule()
                            .async(false)
                            .msgType(WxConsts.XML_MSG_EVENT)
                            .event(key.getKey())
                            .handler(autoReplyhandler)
                            .end();
                    break;
                case click:
                    logger.debug("匹配点击: {}", key.getKey());
                    router.rule()
                            .async(false)
                            .msgType(WxConsts.XML_MSG_EVENT)
                            .eventKey(key.getKey())
                            .handler(autoReplyhandler)
                            .end();
                    break;
                case text:
                    switch (key.getType()) {
                        case CONTAINS:
                            logger.debug("包含关键字 {}", key.getKey());
                            router.rule()
                                    .async(false)
                                    .msgType(WxConsts.XML_MSG_TEXT).rContent(".*" + key.getKey() + ".*").handler(autoReplyhandler).end();
                            break;
                        case MATCHING:
                            logger.debug("完全匹配关键字 {}", key.getKey());
                            router.rule()
                                    .async(false)
                                    .msgType(WxConsts.XML_MSG_TEXT).content(key.getKey()).handler(autoReplyhandler).end();
                            break;
                        case PATTEN:
                            logger.debug("正则匹配关键字 {}", key.getKey());
                            router.rule()
                                    .async(false)
                                    .msgType(WxConsts.XML_MSG_TEXT).rContent(key.getKey()).handler(autoReplyhandler).end();
                    }
                    break;
                case DEFAULT:
                    break;
            }
        }

        final WxMpMessageHandler defaultHandler = new DefaultMessageHandler();
        beanFactory.autowireBean(defaultHandler);
        router.rule()
                .async(false)
                .handler(defaultHandler)
                .end();
        return router;
    }
}
