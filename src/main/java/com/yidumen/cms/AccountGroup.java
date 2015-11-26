package com.yidumen.cms;

/**
 *
 * @author cdm
 */
public enum AccountGroup {

    ADMIN("管理员"), STAFF("志愿者"), USER("普通用户");
    private final String name;

    private AccountGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public static String getNameByOrdinal(int ordinal) {
        for (AccountGroup group : AccountGroup.values()) {
            if (ordinal != group.ordinal()) {
                continue;
            }
            return group.getName();
        }
        return null;
    }

}
