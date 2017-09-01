package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetSignReadOnly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/22.
 */
public class MeetSign {

    private Integer id;
    /**签到人ID*/
    private Integer userId;
    /**会议ID*/
    private String meetId;
    /**对应的会议地址ID*/
    private Integer positionId;
    /**签到经度*/
    private Double signLng;
    /**签到维度*/
    private Double signLat;
    /**签到是否成功*/
    private Boolean signFlag;
    /**签到时间*/
    private Date signTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Double getSignLng() {
        return signLng;
    }

    public void setSignLng(Double signLng) {
        this.signLng = signLng;
    }

    public Double getSignLat() {
        return signLat;
    }

    public void setSignLat(Double signLat) {
        this.signLat = signLat;
    }

    public Boolean getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(Boolean signFlag) {
        this.signFlag = signFlag;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }


    public static MeetSign build(MeetSignReadOnly meetSignReadOnly){
        MeetSign meetSign = new MeetSign();
        if(meetSignReadOnly != null){
            meetSign.setId(meetSignReadOnly.getSignId().intValue());
            meetSign.setUserId(meetSignReadOnly.getUserId().intValue());
            meetSign.setSignFlag("Y".equals(meetSignReadOnly.getSignFlag())?true:false);
            meetSign.setPositionId(meetSignReadOnly.getPosId().intValue());
            meetSign.setSignLat(meetSignReadOnly.getPositionLat() == null || "".equals(meetSignReadOnly.getPositionLat())?0:Double.parseDouble(meetSignReadOnly.getPositionLat()));
            meetSign.setSignLng(meetSignReadOnly.getPositionLng() == null||"".equals(meetSignReadOnly.getPositionLng())?0:Double.parseDouble(meetSignReadOnly.getPositionLng()));
            meetSign.setSignTime(meetSignReadOnly.getCreateTime());
        }
        return meetSign;
    }
}
