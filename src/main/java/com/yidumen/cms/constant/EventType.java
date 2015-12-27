/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yidumen.cms.constant;

/**
 *
 * @author BFSGDS
 */
public enum EventType {
    subscribe("订阅"),
    unsubscribe("取消订阅"),
    CLICK("点击菜单"),
    SCAN("扫描二维码"),
    LOCATION("上报地理位置"),
    VIEW("点击菜单跳转链接");
    
    private final String descript;
    EventType(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }

    @Override
    public String toString() {
        return descript;
    }
    
    
}
