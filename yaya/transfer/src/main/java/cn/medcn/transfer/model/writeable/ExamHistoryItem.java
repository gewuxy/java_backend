package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.ReportAnswerPaperOnly;
import cn.medcn.transfer.utils.LetterUtils;

/**
 * Created by lixuan on 2017/6/22.
 */
public class ExamHistoryItem {
    private Integer id;
    /**所属的答题卡ID*/
    private Integer historyId;
    /**对应的试题ID*/
    private Integer questionId;
    /**正确答案*/
    private String rightKey;
    /**用户提交的答案*/
    private String answer;
    /**该题分数*/
    private Integer point;
    /**得分*/
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRightKey() {
        return rightKey;
    }

    public void setRightKey(String rightKey) {
        this.rightKey = rightKey;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    public static ExamHistoryItem build(ReportAnswerPaperOnly reportAnswerPaperOnly, Integer historyId){
        ExamHistoryItem item = new ExamHistoryItem();
        if(reportAnswerPaperOnly != null){
            item.setAnswer(LetterUtils.numbersToLetters(reportAnswerPaperOnly.getSelAnswer()));
            item.setHistoryId(historyId);
            String qanswer = reportAnswerPaperOnly.getQ_answer();
            if (qanswer == null){
                qanswer = "0";
            }
            qanswer = qanswer.replaceAll("\\s", "");
            if (qanswer.equals("")){
                qanswer = "0";
            }
            item.setRightKey(LetterUtils.numbersToLetters(qanswer));
            item.setId(reportAnswerPaperOnly.getA_id().intValue());
            item.setQuestionId(reportAnswerPaperOnly.getQ_id().intValue());
        }

        return item;
    }
}
