package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.QuestionOptionReadOnly;
import cn.medcn.transfer.model.readonly.QuestionReadOnly;
import cn.medcn.transfer.support.KeyValuePair;
import cn.medcn.transfer.utils.LetterUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class ExamQuestion {
    private Integer id;
    /**试题标题*/
    private String title;
    /**正确答案*/
    private String rightKey;
    /**选项的json数据*/
    private String options;
    /**试题类型 0表示单选 1表示多选 2表示填空 3表示问答*/
    private Integer qtype;
    /**试题拥有者ID*/
    private Integer owner;
    /**是否共享*/
    private Boolean shared;
    /**试题详解*/
    private String detail;
    /**试题难度*/
    private Integer dc;
    /**试题所属科室*/
    private String category;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRightKey() {
        return rightKey;
    }

    public void setRightKey(String rightKey) {
        this.rightKey = rightKey;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getQtype() {
        return qtype;
    }

    public void setQtype(Integer qtype) {
        this.qtype = qtype;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getDc() {
        return dc;
    }

    public void setDc(Integer dc) {
        this.dc = dc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public static ExamQuestion build(QuestionReadOnly questionReadOnly, ExamPaper examPaper){
        ExamQuestion question = new ExamQuestion();
        question.setId(questionReadOnly.getQ_id().intValue());
        question.setShared(false);
        question.setOwner(examPaper.getOwner());
        question.setCreateTime(examPaper.getCreateTime());
        question.setCategory(examPaper.getCategory());
        question.setQtype(Integer.parseInt(questionReadOnly.getQ_type()));
        //设置正确答案
        String rightAnswer = questionReadOnly.getQ_answer();
        if(rightAnswer == null){
            rightAnswer = "";
        }
        rightAnswer = rightAnswer.trim();
        if(rightAnswer.length() > 1){//多选
            question.setRightKey(LetterUtils.numbersToLetters(rightAnswer));
        }else{//单选
            question.setRightKey(LetterUtils.numberToLetter(rightAnswer));
        }
        question.setTitle(questionReadOnly.getQ_title());
        List<KeyValuePair> optionList = new ArrayList<>();
        if(questionReadOnly.getOptionList() != null){
            int index = 1;
            for(QuestionOptionReadOnly option : questionReadOnly.getOptionList()){
                optionList.add(new KeyValuePair(LetterUtils.numberToLetter(index), option.getQ_option()));
                index ++;
            }
        }
        question.setOptions(KeyValuePair.toJSON(optionList));
        return question;
    }
}
