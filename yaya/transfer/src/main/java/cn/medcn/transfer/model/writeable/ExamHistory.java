package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.ReportAnswerPaperOnly;
import cn.medcn.transfer.model.readonly.ReportPaperReadOnly;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/6/22.
 */
public class ExamHistory {
    private Integer id;
    /**试卷ID*/
    private Integer paperId;
    /**考试ID*/
    private Integer examId;
    /**用户ID*/
    private Integer userId;
    /**会议ID*/
    private String meetId;
    /**所属模块ID*/
    private Integer moduleId;

    private Date submitTime;
    /**试卷总分*/
    private Integer totalPoint;
    /**实际得分*/
    private Integer score;
    /**是否已经完成*/
    private Boolean finished;
    /**考试用时*/
    private Integer usedtime;
    /**完成次数*/
    private Integer finishTimes;

    private List<ExamHistoryItem> itemList;

    public List<ExamHistoryItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ExamHistoryItem> itemList) {
        this.itemList = itemList;
    }

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

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Integer getFinishTimes() {
        return finishTimes;
    }

    public void setFinishTimes(Integer finishTimes) {
        this.finishTimes = finishTimes;
    }


    public static ExamHistory build(ReportPaperReadOnly reportPaperReadOnly,String meetId){
        ExamHistory examHistory = new ExamHistory();
        if(reportPaperReadOnly != null){
            examHistory.setId(reportPaperReadOnly.getR_id().intValue());
            examHistory.setUserId(reportPaperReadOnly.getUserId().intValue());
            examHistory.setFinished(true);
            examHistory.setFinishTimes(1);
            examHistory.setTotalPoint(100);
            examHistory.setMeetId(meetId);
            examHistory.setSubmitTime(reportPaperReadOnly.getTjTime());
            examHistory.setScore(reportPaperReadOnly.getScores());
            examHistory.setPaperId(reportPaperReadOnly.getP_id().intValue());
            if(reportPaperReadOnly.getAnswerList() != null){
                List<ExamHistoryItem> items = new ArrayList<>();
                for(ReportAnswerPaperOnly answerPaperOnly : reportPaperReadOnly.getAnswerList()){
                    items.add(ExamHistoryItem.build(answerPaperOnly, examHistory.getId()));
                }
                examHistory.setItemList(items);
            }
        }
        return examHistory;
    }
}
