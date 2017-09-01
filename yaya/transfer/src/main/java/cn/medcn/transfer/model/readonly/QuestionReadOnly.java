package cn.medcn.transfer.model.readonly;

import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class QuestionReadOnly {

    public static final String QUESTION_TYPE_SINGLE = "0";//单选

    public static final String QUESTION_TYPE_MUTIL = "1";//多选


    private Long q_id;//试题ID
    private Long p_id;//试卷ID
    private String q_no;//试题序号
    private String q_title;//试题标题
    private String q_type;//试题类型
    private String q_answer;//正确答案
    private Integer q_score;//试题分数

    private List<QuestionOptionReadOnly> optionList;

    public List<QuestionOptionReadOnly> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionOptionReadOnly> optionList) {
        this.optionList = optionList;
    }

    public Long getQ_id() {
        return q_id;
    }

    public void setQ_id(Long q_id) {
        this.q_id = q_id;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public String getQ_no() {
        return q_no;
    }

    public void setQ_no(String q_no) {
        this.q_no = q_no;
    }

    public String getQ_title() {
        return q_title;
    }

    public void setQ_title(String q_title) {
        this.q_title = q_title;
    }

    public String getQ_type() {
        return q_type;
    }

    public void setQ_type(String q_type) {
        this.q_type = q_type;
    }

    public String getQ_answer() {
        return q_answer;
    }

    public void setQ_answer(String q_answer) {
        this.q_answer = q_answer;
    }

    public Integer getQ_score() {
        return q_score;
    }

    public void setQ_score(Integer q_score) {
        this.q_score = q_score;
    }
}
