/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yidumen.cms.constant;

/**
 *
 * @author 蔡迪旻
 */
public enum KeyType {

    CONTAINS("包含"),
    MATCHING("完全匹配"),
    PATTEN("正则表达式");

    private final String name;

    private KeyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public static KeyType getByOrdinal(int ordinal) {
        for (KeyType keyType : KeyType.values()) {
            if (keyType.ordinal() != ordinal) {
                continue;
            }
            return keyType;
        }
        return null;
    }

}
