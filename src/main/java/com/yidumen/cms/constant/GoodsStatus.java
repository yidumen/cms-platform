package com.yidumen.cms.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author 蔡迪旻
 */
public enum GoodsStatus {
    SUCCESS,
    WAIT,
    ERROR;

    @JsonCreator
    public static GoodsStatus forValue(int value) {
        return GoodsStatus.values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
