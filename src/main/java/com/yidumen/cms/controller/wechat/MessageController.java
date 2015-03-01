package com.yidumen.cms.controller.wechat;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.msg.in.InMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.out.OutMsg;
import com.jfinal.weixin.sdk.msg.out.OutMusicMsg;
import com.jfinal.weixin.sdk.msg.out.OutNewsMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.yidumen.cms.constant.KeyType;
import com.yidumen.cms.constant.WeChatMessageEnum;
import com.yidumen.cms.controller.wechat.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by cdm on 15/2/27.
 */
public final class MessageController extends MsgControllerAdapter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    public void writing() {
        final Long id = getParaToLong(0);
        Record record = Db.findById("writings", id);
        LOG.debug("{}", record);
        LOG.debug("{}", record.getStr("title"));
        setAttr("title", record.getStr("TITLE"));
        setAttr("createTime", record.getDate("CREATETIME"));
        setAttr("author", record.getStr("AUTHOR"));
        setAttr("source", record.getStr("SOURCE"));
        setAttr("content", record.getStr("CONTENT"));
        render("writing.html");
    }
    
    @Override
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        final List<Record> rules = Db.find("SELECT * FROM MSGKEY WHERE REPLYKEY = 'subcsribe'");
        for (Record rule : rules) {
            sendMessage(rule, inFollowEvent);
        }
    }

    @Override
    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken("YDMWeChat");

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(false);
        return ac;
    }

    @Override
    protected void processInTextMsg(InTextMsg inTextMsg) {
        //文本消息后，先读取各种规则，再匹配规则，如果匹配到则回复相应的消息，
        //否则回复默认的消息，如果没有设置默认消息则不回复，没有匹配到规则的消息会被记入消息日志备查。
        final String content = inTextMsg.getContent();
        final List<Record> keys = findKeys();
        for (Record key : keys) {
            final String rs = key.getStr("REPLYKEY");
            switch (KeyType.getByOrdinal(key.getInt("TYPE"))) {
                case CONTAINS:
                    if (content.contains(rs)) {
                        sendMessage(key, inTextMsg);
                        return;
                    }
                    break;
                case MATCHING:
                    if (content.equals(rs)) {
                        sendMessage(key, inTextMsg);
                        return;
                    }
                    break;
                case PATTEN:
                    if (content.matches(rs)) {
                        sendMessage(key, inTextMsg);
                        return;
                    }
            }
        }
        //TODO:之前考虑不周，把用户发送的消息与系统内置的消息混在一起存放，这会造成问题，停用存储用户信息的功能，以后完善
        /*
        final Record wechatMsg = new Record()
                .set("CREATETIME", new Date(inTextMsg.getCreateTime().longValue() * 1000))
                .set("FROMUSERNAME", inTextMsg.getFromUserName())
                .set("TOUSERNAME", inTextMsg.getToUserName());
        Db.save("WECHAT_MSG", wechatMsg);
        final Record wechatTextMsg = new Record()
                .set("ID", Db.queryInt("SELECT LAST_INSERT_ID()"))
                .set("CONTENT", inTextMsg.getContent());
        Db.save("WECHAT_TEXT", wechatTextMsg);
        */

        final Record key = Db.findFirst("SELECT * FROM `MSGKEY` WHERE `REPLYKEY`= 'default'");
        sendMessage(key, inTextMsg);
    }

    private List<Record> findKeys() {
        return Db.find("SELECT * FROM `MSGKEY`");
    }

    @Override
    protected void processInMenuEvent(InMenuEvent inMenuEvent) {
        final List<Record> keys = findKeys();
        final String content = inMenuEvent.getEventKey();
        for (Record key : keys) {
            final String rs = key.getStr("REPLYKEY");
            switch (KeyType.getByOrdinal(key.getInt("TYPE"))) {
                case CONTAINS:
                    if (content.contains(rs)) {
                        sendMessage(key, inMenuEvent);
                        return;
                    }
                    break;
                case MATCHING:
                    if (content.equals(rs)) {
                        sendMessage(key, inMenuEvent);
                        return;
                    }
                    break;
                case PATTEN:
                    if (content.matches(rs)) {
                        sendMessage(key, inMenuEvent);
                        return;
                    }
            }
        }
    }

    private void sendMessage(Record key, InMsg msg) {
        final List<Record> messages = Db.find("SELECT MSG.ID, MSG.DTYPE, MSG.TYPE, CONTENT AS 'text_content', EVENT, PICURL, WECHAT_LINK.DESCRIPTION AS 'link_description', WECHAT_LINK.TITLE AS 'link_title', URL, LABEL, LOCATION_X, LOCATION_Y, SCALE, WECHAT_MUSIC.DESCRIPTION AS 'music_description', HQMUSICURL, MUSICURL, WECHAT_MUSIC.TITLE AS 'music_title', WECHAT_NEWS.ID AS 'NEWS_ID', CLASSNAME FROM (SELECT DISTINCT WECHAT_MSG.ID, DTYPE, CMS_MSG.TYPE FROM WECHAT_MSG INNER JOIN CMS_MSG ON CMS_MSG.MESSAGE_ID = WECHAT_MSG.ID INNER JOIN CMS_MSG_REPLYRULE ON CMS_MSG_REPLYRULE.messages_ID = CMS_MSG.ID INNER JOIN REPLYRULE ON REPLYRULE.ID = CMS_MSG_REPLYRULE.rule_ID AND REPLYRULE.ID = ?) AS MSG  LEFT JOIN WECHAT_TEXT ON MSG.ID = WECHAT_TEXT.ID LEFT JOIN WECHAT_EVENT ON MSG.ID = WECHAT_EVENT.ID LEFT JOIN WECHAT_IMG ON MSG.ID = WECHAT_IMG.ID LEFT JOIN WECHAT_LINK ON MSG.ID = WECHAT_LINK.ID LEFT JOIN WECHAT_LOCATION ON MSG.ID = WECHAT_LOCATION.ID LEFT JOIN WECHAT_MUSIC ON MSG.ID = WECHAT_MUSIC.ID LEFT JOIN WECHAT_NEWS ON MSG.ID = WECHAT_NEWS.ID  LEFT JOIN ACTIONMSG ON MSG.ID = ACTIONMSG.ID;", key.get("RULE_ID"));
        if (null == messages) {
            return;
        }
        for (Record message : messages) {
            final WeChatMessageEnum msgType = WeChatMessageEnum.getByOrdinal(message.getInt("TYPE"));
            switch (msgType) {
                case text:
                    final OutTextMsg outTextMsgmsg = new OutTextMsg(msg);
                    outTextMsgmsg.setContent(message.getStr("text_content"));
                    render(outTextMsgmsg);
                    break;
                case music:
                    final OutMusicMsg outMusicMsg = new OutMusicMsg(msg);
                    outMusicMsg.setTitle(message.getStr("music_title"));
                    outMusicMsg.setMusicUrl(message.getStr("MUSICURL"));
                    outMusicMsg.setHqMusicUrl(message.getStr("HQMUSICURL"));
                    outMusicMsg.setDescription(message.getStr("music_description"));
                    render(outMusicMsg);
                    break;
                case news:
                    final OutNewsMsg outNewsMsg = new OutNewsMsg(msg);
                    final List<Record> aritcles = Db.find("SELECT * FROM ARITCLE INNER JOIN WECHAT_NEWS_ARITCLE ON ARITCLE.ID = WECHAT_NEWS_ARITCLE.aritcles_ID AND WECHAT_NEWS_ARITCLE.WECHAT_NEWS_ID = ?", message.getLong("NEWS_ID"));
                    for (Record aritcle : aritcles) {
                        outNewsMsg.addNews(aritcle.getStr("TITLE"), aritcle.getStr("DESCRIPTION"), aritcle.getStr("PICURL"), aritcle.getStr("URL"));
                    }
                    render(outNewsMsg);
                    break;
                case action:
                    try {
                        final Action at = (Action) Class.forName(message.getStr("CLASSNAME")).newInstance();
                        final OutMsg reply = at.action(msg);
                        if (reply == null) {
                            return;
                        }
                        render(reply);
                    } catch (ReflectiveOperationException ex) {
                        return;
                    }
            }
        }
    }
}
