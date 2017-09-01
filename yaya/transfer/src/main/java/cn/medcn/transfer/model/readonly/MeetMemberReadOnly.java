package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/22.
 */
public class MeetMemberReadOnly {

    private Long memberId;			//成员表id  主键
    private Long groupId;//会议群id   外键
    private String attender;			//会议参与者   用户名
    private Date attendTime;	//加入会议时间
    private Date lookmtjlOverTime; //查看完 会议消息记录 的时间

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getAttender() {
        return attender;
    }

    public void setAttender(String attender) {
        this.attender = attender;
    }

    public Date getAttendTime() {
        return attendTime;
    }

    public void setAttendTime(Date attendTime) {
        this.attendTime = attendTime;
    }

    public Date getLookmtjlOverTime() {
        return lookmtjlOverTime;
    }

    public void setLookmtjlOverTime(Date lookmtjlOverTime) {
        this.lookmtjlOverTime = lookmtjlOverTime;
    }
}
