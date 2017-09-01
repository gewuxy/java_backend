package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/16.
 */
public class PositionReadOnly {

    private Long posId;
    private Long meetingId;
    private String positionLng;
    private String positionLat;
    private String positionName;
    private Date signstartTime;
    private Date signendTime;


    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getPositionLng() {
        return positionLng;
    }

    public void setPositionLng(String positionLng) {
        this.positionLng = positionLng;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getSignstartTime() {
        return signstartTime;
    }

    public void setSignstartTime(Date signstartTime) {
        this.signstartTime = signstartTime;
    }

    public Date getSignendTime() {
        return signendTime;
    }

    public void setSignendTime(Date signendTime) {
        this.signendTime = signendTime;
    }
}
