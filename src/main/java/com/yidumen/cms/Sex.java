package com.yidumen.cms;

/**
 * Created by cdm on 2015/10/15.
 */
public enum Sex {
    UNDEFINE("未知"),
    MALE("男"),
    FEMALE("女");
    private final String sexName;

    Sex(String sexName) {
        this.sexName = sexName;
    }

    public String getSexName() {
        return sexName;
    }
}
