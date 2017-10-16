package cn.medcn.meet.model;

import cn.medcn.common.utils.KeyValuePair;
import cn.medcn.common.utils.LetterUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/4/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_survey_question")
public class SurveyQuestion implements Serializable{

    @Id
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

    //用户选择的答案
    @Transient
    private String answer;

    @Transient
    private String[] optionArr;

    public void optionArrToJSON(){
        if(optionArr != null && optionArr.length > 0){
            optionList = Lists.newArrayList();
            for(int i =0 ; i < optionArr.length ; i++){
                KeyValuePair pair = new KeyValuePair(LetterUtils.numberToLetter(i+1), optionArr[i]);
                optionList.add(pair);
            }
            this.options = JSON.toJSONString(optionList);
        }
    }

    @Transient
    private List<KeyValuePair> optionList;

    public List<KeyValuePair> getOptionList(){
        if(this.optionList!=null){
            return this.optionList;
        }
        List<KeyValuePair> list = Lists.newArrayList();
        if(!StringUtils.isEmpty(this.options)){
            list = JSONArray.parseArray(this.options, KeyValuePair.class);
        }
        return list;
    }

    public enum QuestionType{
        SINGLE_CHOICE(0,"单选"),
        MULTIPLE_CHOICE(1,"多选"),
        FILL_BLANK(2,"填空"),
        QUESTION_ANSERE(3,"问答");

        private Integer type;

        private String label;

        public Integer getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        QuestionType(Integer type, String label){
            this.type = type;
            this.label = label;
        }
    }
}
