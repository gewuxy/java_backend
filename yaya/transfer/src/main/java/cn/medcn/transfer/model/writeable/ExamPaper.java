package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;
import cn.medcn.transfer.model.readonly.PaperReadOnly;
import cn.medcn.transfer.model.readonly.QuestionReadOnly;
import cn.medcn.transfer.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class ExamPaper {
    private Integer id;
    /**试卷名*/
    private String paperName;
    /**试卷总分*/
    private Integer totalPoint;
    /**评分规则 nxm+nxm n为分数 m为题数*/
    private String scorePolicy;
    /**创建时间*/
    private Date createTime;
    /**试卷拥有者ID*/
    private Integer owner;
    /**是否共享*/
    private Boolean shared;
    /**试卷科室*/
    private String category;

    private List<ExamQuestion> questionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getScorePolicy() {
        return scorePolicy;
    }

    public void setScorePolicy(String scorePolicy) {
        this.scorePolicy = scorePolicy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ExamQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<ExamQuestion> questionList) {
        this.questionList = questionList;
    }



    public static ExamPaper build(MeetSourceReadOnly meetSourceReadOnly, PaperReadOnly paperReadOnly, Integer owner){
        ExamPaper examPaper = new ExamPaper();
        examPaper.setId(paperReadOnly.getP_id().intValue());
        examPaper.setCategory(meetSourceReadOnly.getKind());
        examPaper.setCreateTime(DateUtils.parseDate(meetSourceReadOnly.getStartTime()));
        examPaper.setOwner(owner);
        examPaper.setPaperName(paperReadOnly.getP_title());
        examPaper.setTotalPoint(100);
        examPaper.setShared(false);
        List<ExamQuestion> questionList = new ArrayList<>();
        if(paperReadOnly.getQuestionList() != null){
            for(QuestionReadOnly question : paperReadOnly.getQuestionList()){
                questionList.add(ExamQuestion.build(question, examPaper));
            }
        }
        examPaper.setQuestionList(questionList);
        return examPaper;
    }
}
