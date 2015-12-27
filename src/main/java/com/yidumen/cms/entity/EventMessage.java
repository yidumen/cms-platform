package com.yidumen.cms.entity;


import com.yidumen.cms.constant.EventType;

import javax.persistence.*;

@Entity
@Table(name = "wechat_message_event")
public class EventMessage extends Message {

    @Column(name = "event_type")
    @Enumerated(EnumType.ORDINAL)
    private EventType event;

    @Column(name = "event_key")
    private String eventKey;
    @Column(name = "common_info_1", length = 32)
    private String commonCode;
    @Column(name = "common_info_2", length = 64)
    private String commonInfo;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "poi")
    private Double precision;

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public String getCommonCode() {
        return commonCode;
    }

    public void setCommonCode(String commonCode) {
        this.commonCode = commonCode;
    }

    public String getCommonInfo() {
        return commonInfo;
    }

    public void setCommonInfo(String commonInfo) {
        this.commonInfo = commonInfo;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double locationX) {
        this.latitude = locationX;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double locationY) {
        this.longitude = locationY;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double poiname) {
        this.precision = poiname;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EventMessage that = (EventMessage) o;

        if (getEvent() != that.getEvent()) return false;
        if (getEventKey() != null ? !getEventKey().equals(that.getEventKey()) : that.getEventKey() != null)
            return false;
        if (getCommonCode() != null ? !getCommonCode().equals(that.getCommonCode()) : that.getCommonCode() != null)
            return false;
        if (getCommonInfo() != null ? !getCommonInfo().equals(that.getCommonInfo()) : that.getCommonInfo() != null)
            return false;
        if (getLatitude() != null ? !getLatitude().equals(that.getLatitude()) : that.getLatitude() != null)
            return false;
        if (getLongitude() != null ? !getLongitude().equals(that.getLongitude()) : that.getLongitude() != null)
            return false;
        return !(getPrecision() != null ? !getPrecision().equals(that.getPrecision()) : that.getPrecision() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getEvent() != null ? getEvent().hashCode() : 0);
        result = 31 * result + (getEventKey() != null ? getEventKey().hashCode() : 0);
        result = 31 * result + (getCommonCode() != null ? getCommonCode().hashCode() : 0);
        result = 31 * result + (getCommonInfo() != null ? getCommonInfo().hashCode() : 0);
        result = 31 * result + (getLatitude() != null ? getLatitude().hashCode() : 0);
        result = 31 * result + (getLongitude() != null ? getLongitude().hashCode() : 0);
        result = 31 * result + (getPrecision() != null ? getPrecision().hashCode() : 0);
        return result;
    }
}
