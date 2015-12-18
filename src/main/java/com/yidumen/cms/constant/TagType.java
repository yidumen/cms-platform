package com.yidumen.cms.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author cdm
 */
public enum TagType {
    CONTENT, GROUP, COLUMN;

    @JsonCreator
    public static TagType forValue(int value) {
        return TagType.values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
