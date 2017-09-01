package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.PositionReadOnly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/19.
 */
public class MeetPosition {

    private Integer id;

    private String meetId;

    private Integer moduleId;

    private Double positionLng;

    private Double positionLat;

    private String positionName;
    /**签到开始时间*/
    private Date startTime;
    /**签到结束时间*/
    private Date endTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Double getPositionLng() {
        return positionLng;
    }

    public void setPositionLng(Double positionLng) {
        this.positionLng = positionLng;
    }

    public Double getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(Double positionLat) {
        this.positionLat = positionLat;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public static MeetPosition build(PositionReadOnly source){
        MeetPosition position = new MeetPosition();
        if(source != null){
            position.setId(source.getPosId().intValue());
            position.setStartTime(source.getSignstartTime());
            position.setEndTime(source.getSignendTime());
            position.setPositionLat(source.getPositionLat()== null || "".equals(source.getPositionLat())?0:Double.valueOf(source.getPositionLat()));
            position.setPositionLng(source.getPositionLng() == null || "".equals(source.getPositionLng())?0:Double.valueOf(source.getPositionLng()));
            position.setPositionName(source.getPositionName());
        }
        return position;
    }
}
