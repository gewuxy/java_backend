package cn.medcn.meet.dto;

import cn.medcn.common.utils.KeyValuePair;
import cn.medcn.common.utils.LetterUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Transient;
import java.util.List;

/**
 * Created by Liuchangling on 2017/6/13.
 */
@NoArgsConstructor
@Data
public class SurveyRecordDTO {
    private Integer id;
    // 题号
    private Integer sort;

    // 题目
    private String title;

    // 题目类型
    private Integer qtype;

    // 选项内容dto
    private List SurveyRecordItemDTO;

    /**试题选项字符串*/
    private String options;

    // 会议名称
    private String meetName;

    // 参加问卷总人数
    private Integer totalCount;

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
}
