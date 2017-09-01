package cn.medcn.transfer.model.readonly;

import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class PaperReadOnly {

    public static final String TYPE_EXAM = "考试";

    public static final String TYPE_SURVEY = "问卷调查";

    private Long p_id;//试卷id
    private Long meetingId;//会议ID
    private String p_title;//试卷标题
    private Integer p_time;//考试用时
    private Integer p_total_num;//总题数
    private String p_type;//试卷类型

    private List<QuestionReadOnly> questionList;


    public List<QuestionReadOnly> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionReadOnly> questionList) {
        this.questionList = questionList;
    }

    public static String getTypeExam() {
        return TYPE_EXAM;
    }

    public static String getTypeSurvey() {
        return TYPE_SURVEY;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getP_title() {
        return p_title;
    }

    public void setP_title(String p_title) {
        this.p_title = p_title;
    }

    public Integer getP_time() {
        return p_time;
    }

    public void setP_time(Integer p_time) {
        this.p_time = p_time;
    }

    public Integer getP_total_num() {
        return p_total_num;
    }

    public void setP_total_num(Integer p_total_num) {
        this.p_total_num = p_total_num;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }
}
