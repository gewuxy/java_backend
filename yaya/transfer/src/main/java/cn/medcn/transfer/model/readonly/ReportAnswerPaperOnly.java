package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/6/22.
 */
public class ReportAnswerPaperOnly {

    private Long a_id;
    private Long q_id;
    private Long userId;
    private String q_answer;
    private String selAnswer;

    public String getQ_answer() {
        return q_answer;
    }

    public void setQ_answer(String q_answer) {
        this.q_answer = q_answer;
    }

    public Long getA_id() {
        return a_id;
    }

    public void setA_id(Long a_id) {
        this.a_id = a_id;
    }

    public Long getQ_id() {
        return q_id;
    }

    public void setQ_id(Long q_id) {
        this.q_id = q_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSelAnswer() {
        return selAnswer;
    }

    public void setSelAnswer(String selAnswer) {
        this.selAnswer = selAnswer;
    }
}
