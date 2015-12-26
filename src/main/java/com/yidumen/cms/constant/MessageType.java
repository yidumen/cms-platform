/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yidumen.cms.constant;

/**
 * @author BFSGDS
 */
public enum MessageType {

    text("文本消息"),
    image("图片消息"),
    location("地理位置消息"),
    link("链接消息"),
    event("事件推送"),
    music("音乐消息"),
    news("图文消息"),
    action("行为消息"),
    voice("语音消息"),
    click("菜单点击"),
    DEFAULT("默认消息");

    private final String descript;

    private MessageType(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }

    public static MessageType getByOrdinal(int ordinal) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.ordinal() != ordinal) {
                continue;
            }
            return messageType;
        }
        return null;
    }
}
