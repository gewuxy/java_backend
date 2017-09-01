package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PaperReadOnly;
import cn.medcn.transfer.utils.DateUtils;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/19.
 */
public class MeetExam {

    private Integer id;
    /**会议ID*/
    private String meetId;
    /**模块ID*/
    private Integer moduleId;
    /**用时*/
    private Integer usetime;
    /**试卷ID*/
    private Integer paperId;
    /**试题乱序*/
    private Boolean questionReorder;

    private Date startTime;

    private Date endTime;
    /**时间限制类型 0表示用时 1表示时间段*/
    private Integer timeLimitType;
    /**及格分数*/
    private Integer passScore;
    /**可重考次数*/
    private Integer resitTimes;

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

    public Integer getUsetime() {
        return usetime;
    }

    public void setUsetime(Integer usetime) {
        this.usetime = usetime;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Boolean getQuestionReorder() {
        return questionReorder;
    }

    public void setQuestionReorder(Boolean questionReorder) {
        this.questionReorder = questionReorder;
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

    public Integer getTimeLimitType() {
        return timeLimitType;
    }

    public void setTimeLimitType(Integer timeLimitType) {
        this.timeLimitType = timeLimitType;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public Integer getResitTimes() {
        return resitTimes;
    }

    public void setResitTimes(Integer resitTimes) {
        this.resitTimes = resitTimes;
    }


    public static MeetExam build(MeetSourceReadOnly meetSourceReadOnly, PaperReadOnly paperReadOnly,String meetId, Integer moduleId){
        MeetExam meetExam = new MeetExam();
        meetExam.setMeetId(meetId);
        meetExam.setModuleId(moduleId);
        meetExam.setStartTime(DateUtils.parseDate(meetSourceReadOnly.getStartTime()));
        meetExam.setEndTime(DateUtils.parseDate(meetSourceReadOnly.getEndTime()));
        meetExam.setPassScore(60);
        meetExam.setResitTimes(3);
        meetExam.setTimeLimitType(0);
        meetExam.setUsetime(paperReadOnly.getP_time());
        return meetExam;
    }
}
