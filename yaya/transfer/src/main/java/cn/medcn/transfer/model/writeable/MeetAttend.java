package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetMemberReadOnly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/22.
 */
public class MeetAttend {
    private Integer id;
    /**会议ID*/
    private String meetId;
    /**用户ID*/
    private Integer userId;
    /**开始时间*/
    private Date startTime;
    /**结束时间*/
    private Date endTime;
    /**用时*/
    private Integer usetime;
    /**会议模块ID*/
    private Integer moduleId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getUsetime() {
        return usetime;
    }

    public void setUsetime(Integer usetime) {
        this.usetime = usetime;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }


    public static MeetAttend build(MeetMemberReadOnly meetMemberReadOnly, String meetId, Integer userId){
        MeetAttend meetAttend = new MeetAttend();
        if(meetMemberReadOnly != null){
            meetAttend.setMeetId(meetId);
            meetAttend.setStartTime(meetMemberReadOnly.getAttendTime());
            meetAttend.setEndTime(meetMemberReadOnly.getLookmtjlOverTime());
            //meetAttend.setId(meetMemberReadOnly.getMemberId().intValue());
            meetAttend.setUserId(userId);
            //Long usedtime = meetAttend.getEndTime() == null ? 0L:(meetAttend.getEndTime().getTime() - meetAttend.getStartTime().getTime())/1000;
            //meetAttend.setUsetime(usedtime.intValue());
        }
        return meetAttend;
    }
}
