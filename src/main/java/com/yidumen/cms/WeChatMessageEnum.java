/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yidumen.cms;

/**
 *
 * @author BFSGDS
 */
public enum WeChatMessageEnum {

    text("文本消息"),
    image("图片消息"),
    location("地理位置消息"),
    link("链接消息"),
    event("事件推送"),
    music("音乐消息"),
    news("图文消息"),
    action("行为消息");
    
    private final String descript;

    private WeChatMessageEnum(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }
    
    public static WeChatMessageEnum getByOrdinal (int ordinal) {
        for (WeChatMessageEnum weChatMessageEnum : WeChatMessageEnum.values()) {
            if (weChatMessageEnum.ordinal() != ordinal) {
                continue;
            }
            return weChatMessageEnum;
        }
        return null;
    }
    
}
