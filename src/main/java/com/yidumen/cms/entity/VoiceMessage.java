package com.yidumen.cms.entity;

/**
 * Created by cdm on 2015/10/17.
 */
public class VoiceMessage extends Message {
    private String mediaId;
    private String format;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoiceMessage that = (VoiceMessage) o;

        if (getMediaId() != null ? !getMediaId().equals(that.getMediaId()) : that.getMediaId() != null) return false;
        return !(getFormat() != null ? !getFormat().equals(that.getFormat()) : that.getFormat() != null);

    }

    @Override
    public int hashCode() {
        int result = getMediaId() != null ? getMediaId().hashCode() : 0;
        result = 31 * result + (getFormat() != null ? getFormat().hashCode() : 0);
        return result;
    }
}
