package com.yidumen.cms.view.wechat;

import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 蔡迪旻
 *         2015年11月30日
 */
@RestController
@RequestMapping("wechat")
public final class MessageController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpMessageRouter router;

    @Transactional(readOnly = true)
    @RequestMapping(value = "message", produces = MediaType.APPLICATION_XML_VALUE)
    public String processMessage(@RequestBody String message,
                                 @RequestParam String signature,
                                 @RequestParam String nonce,
                                 @RequestParam String timestamp,
                                 @RequestParam String echostr,
                                 @RequestParam("encrypt_type") String encryptType,
                                 @RequestParam("msg_signature") String msgSignature) {
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            return "";
        }
        if (StringUtils.isNotBlank(echostr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            return echostr;
        }
        encryptType = StringUtils.isBlank(encryptType) ? "raw" : encryptType;
        WxMpXmlMessage inMessage;
        if ("raw".equals(encryptType)) {
            // 明文传输的消息
            inMessage = WxMpXmlMessage.fromXml(message);
            return router.route(inMessage).toXml();
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            inMessage = WxMpXmlMessage.fromEncryptedXml(message, wxMpConfigStorage, timestamp, nonce, msgSignature);
            return router.route(inMessage).toEncryptedXml(wxMpConfigStorage);

        }
        return "";
    }
}
