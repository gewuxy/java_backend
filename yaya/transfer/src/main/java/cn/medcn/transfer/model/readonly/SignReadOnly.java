package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/16.
 */
public class SignReadOnly {

    private Long signId;//签到ID
    private Long positionId;//会场地址ID
    private Long doctorId;//签到人员ID
    private String positionLng;
    private String positionLat;//
    private Date createTime;//签到时间
    private String signFlag;//签到是否成功


    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag;
    }
}
