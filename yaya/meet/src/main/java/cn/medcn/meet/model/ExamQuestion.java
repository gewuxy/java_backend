package cn.medcn.meet.model;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.utils.KeyValuePair;
import cn.medcn.common.utils.LetterUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_exam_question")
public class ExamQuestion implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
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

    @Transient
    private String qtypeName;

    @Transient
    private Integer point;

    //用户选择的答案
    @Transient
    private String answer;

    @Transient
    private List<KeyValuePair> optionList;

    @Transient
    private String[] optionArr;

    @Transient
    private String[] rightKeyArr;

    public String getQtypeName(){
        return QuestionType.values()[qtype].getLabel();
    }

    public void rightKeyArrToRightKey(){
        if(this.rightKeyArr != null){
            StringBuilder builder = new StringBuilder();
            for(String key:rightKeyArr){
                builder.append(LetterUtils.numberToLetter(Integer.parseInt(key)));
            }
            this.rightKey = builder.toString();
        }
    }

    public void optionArrToJSON() throws SystemException {
        if(optionArr != null && optionArr.length > 0){
            if (optionArr.length > 26) {
                throw new SystemException("选项不能多于26个");
            }
            optionList = Lists.newArrayList();
            for(int i =0 ; i < optionArr.length ; i++){
                KeyValuePair pair = new KeyValuePair(LetterUtils.numberToLetter(i+1), optionArr[i]);
                optionList.add(pair);
            }
            this.options = JSON.toJSONString(optionList);
        }
    }

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

    public static void main(String[] args) {
        ExamQuestion question = new ExamQuestion();
        question.setQtype(1);
        System.out.println(question.getQtypeName());
    }
}
