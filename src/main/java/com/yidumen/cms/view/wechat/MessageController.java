package com.yidumen.cms.view.wechat;

import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 蔡迪旻
 *         2015年11月30日
 */
@RestController
@RequestMapping("wechat")
public class MessageController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpMessageRouter router;

    @Transactional(readOnly = true)
    @RequestMapping(value = "message", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_XML_VALUE)
    public String processMessage(@RequestBody(required = false) String message,
                                 @RequestParam(required = false) String signature,
                                 @RequestParam(required = false) String nonce,
                                 @RequestParam(required = false) String timestamp,
                                 @RequestParam(required = false) String echostr,
                                 @RequestParam(required = false, name = "encrypt_type") String encryptType,
                                 @RequestParam(required = false, name = "msg_signature") String msgSignature) {
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            LOG.info("收到非公众平台消息.忽略");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return "";
        }
        if (StringUtils.isNotBlank(echostr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            LOG.info("收到公众平台消息验证信息 {}", echostr);
            return echostr;
        }
        encryptType = StringUtils.isBlank(encryptType) ? "raw" : encryptType;
        WxMpXmlMessage inMessage;
        if ("raw".equals(encryptType)) {
            // 明文传输的消息
            inMessage = WxMpXmlMessage.fromXml(message);
            final WxMpXmlOutMessage outMessage = router.route(inMessage);
            if (outMessage != null) {
                return outMessage.toXml();
            }
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            inMessage = WxMpXmlMessage.fromEncryptedXml(message, wxMpConfigStorage, timestamp, nonce, msgSignature);
            final WxMpXmlOutMessage outMessage = router.route(inMessage);
            if (outMessage != null) {
                return outMessage.toEncryptedXml(wxMpConfigStorage);
            }

        }
        return "";
    }
}
