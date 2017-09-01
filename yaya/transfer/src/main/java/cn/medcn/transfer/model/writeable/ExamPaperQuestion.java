package cn.medcn.transfer.model.writeable;

/**
 * Created by lixuan on 2017/6/19.
 */
public class ExamPaperQuestion {

    private Integer id;
    /**试卷ID*/
    private Integer paperId;
    /**试题ID*/
    private Integer questionId;
    /**分值*/
    private Integer point;
    /**在试题中的排序*/
    private Integer sort;

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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
