package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.QuestionOptionReadOnly;
import cn.medcn.transfer.model.readonly.QuestionReadOnly;
import cn.medcn.transfer.support.KeyValuePair;
import cn.medcn.transfer.utils.LetterUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuan on 2017/6/16.
 */
public class SurveyQuestion {
    private Integer id;
    /**试题标题*/
    private String title;
    /**试题选项字符串*/
    private String options;
    /**排序号*/
    private Integer sort;
    /**试题类型 跟考试试题一样*/
    private Integer qtype;

    private Integer paperId;


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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getQtype() {
        return qtype;
    }

    public void setQtype(Integer qtype) {
        this.qtype = qtype;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }


    public static SurveyQuestion build(QuestionReadOnly questionReadOnly){
        SurveyQuestion question = new SurveyQuestion();
        question.setId(questionReadOnly.getQ_id().intValue());
        String qtype = questionReadOnly.getQ_type();
        if(qtype == null || "".equals(qtype)){
            qtype = "0";
        }
        question.setQtype(Integer.parseInt(qtype));
        question.setTitle(questionReadOnly.getQ_title() == null?"":questionReadOnly.getQ_title());

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
