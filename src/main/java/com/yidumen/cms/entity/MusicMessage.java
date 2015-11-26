package com.yidumen.cms.entity;

import com.yuntaisi.cms.dao.entity.Audio;

public class MusicMessage extends Message {
   
    private String description;
    private Audio audio;
    private String thumbMediaId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MusicMessage that = (MusicMessage) o;

        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getAudio() != null ? !getAudio().equals(that.getAudio()) : that.getAudio() != null) return false;
        return !(getThumbMediaId() != null ? !getThumbMediaId().equals(that.getThumbMediaId()) : that.getThumbMediaId() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAudio() != null ? getAudio().hashCode() : 0);
        result = 31 * result + (getThumbMediaId() != null ? getThumbMediaId().hashCode() : 0);
        return result;
    }
}
