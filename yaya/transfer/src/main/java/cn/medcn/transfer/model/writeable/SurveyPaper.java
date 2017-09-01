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
public class SurveyPaper {

    private Integer id;

    private String paperName;

    private Date createTime;

    private String category;

    private Integer owner;

    private List<SurveyQuestion> questionList;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public List<SurveyQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<SurveyQuestion> questionList) {
        this.questionList = questionList;
    }


    public static SurveyPaper build(MeetSourceReadOnly meetSourceReadOnly, PaperReadOnly paper, Integer owner){
        SurveyPaper surveyPaper = new SurveyPaper();
        surveyPaper.setId(paper.getP_id().intValue());
        surveyPaper.setOwner(owner);
        surveyPaper.setPaperName(paper.getP_title());
        surveyPaper.setCategory(meetSourceReadOnly.getKind());
        surveyPaper.setCreateTime(DateUtils.parseDate(meetSourceReadOnly.getStartTime()));
        if(paper.getQuestionList() != null && paper.getQuestionList().size() > 0){
            List<SurveyQuestion> questionList = new ArrayList<>();
            for(QuestionReadOnly questionReadOnly : paper.getQuestionList()){
                questionList.add(SurveyQuestion.build(questionReadOnly));
            }
            surveyPaper.setQuestionList(questionList);
        }
        return surveyPaper;
    }
}
