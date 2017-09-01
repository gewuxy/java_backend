package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.VideoRecordReadOnly;

import java.util.Date;

/**
 * Created by lixuan on 2017/6/22.
 */
public class VideoHistory {

    private Integer id;

    private Integer userId;

    private Integer detailId;

    private String meetId;

    private Integer moduleId;

    private Integer courseId;

    private Boolean finished;

    private Integer usedtime;

    private Date startTime;

    private Date endTime;

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

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getUsedtime() {
        return usedtime;
    }

    public void setUsedtime(Integer usedtime) {
        this.usedtime = usedtime;
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


    public static VideoHistory build(VideoRecordReadOnly recordReadOnly, String meetId, Integer videoId){
        VideoHistory videoHistory = new VideoHistory();
        if(recordReadOnly != null){
            videoHistory.setId(videoHistory.getId());
            videoHistory.setMeetId(meetId);
            videoHistory.setDetailId(videoId);
            videoHistory.setUserId(recordReadOnly.getUserId().intValue());
            videoHistory.setUsedtime(recordReadOnly.getPlayTime());
            videoHistory.setFinished(recordReadOnly.getPlayPercent().startsWith("100"));
        }
        return videoHistory;
    }
}
