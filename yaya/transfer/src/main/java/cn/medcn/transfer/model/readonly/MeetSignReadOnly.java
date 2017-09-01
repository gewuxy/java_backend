package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/22.
 */
public class MeetSignReadOnly {

    private Long signId;
    private Long posId;
    private Long userId;
    private String positionLng;
    private String positionLat;
    private Date createTime;
    private String signFlag;
    private String uniqueKey;

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
