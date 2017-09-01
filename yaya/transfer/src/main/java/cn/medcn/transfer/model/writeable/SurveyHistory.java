package cn.medcn.transfer.model.writeable;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/6/22.
 */
public class SurveyHistory {
    private Integer id;

    private Integer paperId;

    private String meetId;

    private Integer moduleId;

    private Integer surveyId;
    /**提交时间*/
    private Date submitTime;
    /**用户id*/
    private Integer userId;
    /**作答用时*/
    private Integer usedtime;

    private List<SurveyHistoryItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
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

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUsedtime() {
        return usedtime;
    }

    public void setUsedtime(Integer usedtime) {
        this.usedtime = usedtime;
    }

    public List<SurveyHistoryItem> getItems() {
        return items;
    }

    public void setItems(List<SurveyHistoryItem> items) {
        this.items = items;
    }


    public static SurveyHistory build(Integer userId, String meetId, Integer paperId){
        SurveyHistory history = new SurveyHistory();

        history.setUserId(userId);
        history.setMeetId(meetId);
        history.setPaperId(paperId);

        return history;
    }
}
