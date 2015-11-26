package com.yidumen.cms.entity;

import com.yuntaisi.wechat.EventType;

public class EventMessage extends Message {

    private EventType event;
    private String eventKey;
    private String commonCode;
    private String commonInfo;
    private Double locationX;
    private Double locationY;
    private String poiname;

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

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
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
        if (getLocationX() != null ? !getLocationX().equals(that.getLocationX()) : that.getLocationX() != null)
            return false;
        if (getLocationY() != null ? !getLocationY().equals(that.getLocationY()) : that.getLocationY() != null)
            return false;
        return !(getPoiname() != null ? !getPoiname().equals(that.getPoiname()) : that.getPoiname() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getEvent() != null ? getEvent().hashCode() : 0);
        result = 31 * result + (getEventKey() != null ? getEventKey().hashCode() : 0);
        result = 31 * result + (getCommonCode() != null ? getCommonCode().hashCode() : 0);
        result = 31 * result + (getCommonInfo() != null ? getCommonInfo().hashCode() : 0);
        result = 31 * result + (getLocationX() != null ? getLocationX().hashCode() : 0);
        result = 31 * result + (getLocationY() != null ? getLocationY().hashCode() : 0);
        result = 31 * result + (getPoiname() != null ? getPoiname().hashCode() : 0);
        return result;
    }
}
