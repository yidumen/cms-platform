package com.yidumen.cms;

/**
 *
 * @author 蔡迪旻<yidumen.com>
 */
public enum VideoResolution {

    SHD("720", "超清"),
    HD("480", "高清"),
    SD("360", "标清"),
    FLOW("180", "流畅");

    private final String resolution;
    private final String descript;

    private VideoResolution(String resolution, String descript) {
        this.resolution = resolution;
        this.descript = descript;
    }
    
    public static VideoResolution getByDescript(String descript) {
        for (VideoResolution vr : VideoResolution.values()) {
            if (vr.getDescript().equals(descript)) {
                return vr;
            }
        }
        return null;
    }
    
    public static VideoResolution getByOrdinal(int ordinal) {
        for (VideoResolution value : VideoResolution.values()) {
            if (value.ordinal() != ordinal) {
                continue;
            }
            return value;
        }
        return null;
    }

    public String getResolution() {
        return resolution;
    }

    public String getDescript() {
        return descript;
    }

}
