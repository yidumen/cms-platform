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

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

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

    @RequestMapping(value = "message", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String plug(@RequestParam(required = false) String signature,
                       @RequestParam(required = false) String nonce,
                       @RequestParam(required = false) String timestamp,
                       @RequestParam(required = false) String echostr) {
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            LOG.info("收到非公众平台消息.忽略");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return "";
        }
        LOG.info("收到公众平台消息验证信息 {}", echostr);
        return echostr;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "message", method = RequestMethod.POST, consumes = "text/xml",produces = "text/xml;charset=utf-8")
    public String processMessage(@RequestBody(required = false) String message,
                                 HttpServletRequest request,
                                 @RequestParam(required = false) String signature,
                                 @RequestParam(required = false) String nonce,
                                 @RequestParam(required = false) String timestamp,
                                 @RequestParam(required = false, name = "encrypt_type") String encryptType,
                                 @RequestParam(required = false, name = "msg_signature") String msgSignature) throws UnsupportedEncodingException {
        LOG.debug("收到消息: {}", message);
        LOG.debug("编码为 {}", request.getCharacterEncoding());
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            LOG.info("收到非公众平台消息.忽略");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return "";
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
