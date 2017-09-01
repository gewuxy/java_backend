package cn.medcn.transfer.model.readonly;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class ReportPaperReadOnly {

    private Long r_id;
    private Long p_id;
    private Long userId;
    private String userName;
    private Date answerTime;
    private Date tjTime;
    private Integer scores;

    private List<ReportAnswerPaperOnly> answerList;

    public List<ReportAnswerPaperOnly> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<ReportAnswerPaperOnly> answerList) {
        this.answerList = answerList;
    }

    public Long getR_id() {
        return r_id;
    }

    public void setR_id(Long r_id) {
        this.r_id = r_id;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public Date getTjTime() {
        return tjTime;
    }

    public void setTjTime(Date tjTime) {
        this.tjTime = tjTime;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }
}
