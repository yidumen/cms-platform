package com.yidumen.cms.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author 蔡迪旻<yidumen.com>
 */
public enum VideoStatus {
    PUBLISH("发布"),
    VERIFY("审核"),
    ARCHIVE("存档");

    private final String descript;

    private VideoStatus(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }

    public static VideoStatus getByDescript(String descript) {
        for (VideoStatus status : VideoStatus.values()) {
            if (descript.equals(status.getDescript())) {
                return status;
            }
        }
        return null;
    }

    @JsonCreator
    public static VideoStatus forValue(int value) {
        return VideoStatus.values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
