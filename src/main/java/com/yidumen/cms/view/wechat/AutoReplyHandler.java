package com.yidumen.cms.view.wechat;

import com.yidumen.cms.entity.ReplyMessage;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.Map;

/**
 * @author 蔡迪旻
 *         2015年11月29日
 */
public class AutoReplyHandler implements WxMpMessageHandler {
    @Autowired
    private String baseUrl;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    private final ReplyMessage platformMessage;

    public AutoReplyHandler(ReplyMessage platformMessage) {
        this.platformMessage = platformMessage;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        return WechatUtil.parseMessage(beanFactory, wxMessage, context, wxMpService, sessionManager, this.platformMessage, this.baseUrl);
    }
}
