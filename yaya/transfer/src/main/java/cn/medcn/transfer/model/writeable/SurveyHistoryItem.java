package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.ReportSurveyItemReadOnly;
import cn.medcn.transfer.utils.LetterUtils;

import java.util.List;

/**
 * Created by lixuan on 2017/6/22.
 */
public class SurveyHistoryItem {
    private Integer id;

    private String answer;

    private Integer historyId;

    private Integer questionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }


    public static SurveyHistoryItem build(ReportSurveyItemReadOnly reportSurveyItemReadOnly){
        SurveyHistoryItem item = new SurveyHistoryItem();
        if(reportSurveyItemReadOnly != null){
            item.setId(reportSurveyItemReadOnly.getR_id().intValue());
            item.setQuestionId(reportSurveyItemReadOnly.getQ_id().intValue());
        }
        return item;
    }
}
