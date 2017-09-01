package cn.medcn.meet.dto;

import cn.medcn.common.utils.KeyValuePair;
import cn.medcn.meet.model.SurveyQuestion;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
public class SurveyQuestionDTO implements Serializable{

    private Integer id;
    /**试题标题*/
    private String title;
    /**排序号*/
    private Integer sort;
    /**试题类型 跟考试试题一样*/
    private Integer qtype;

    private List<KeyValuePair> options;

    public static SurveyQuestionDTO build(SurveyQuestion question){
        SurveyQuestionDTO dto = new SurveyQuestionDTO();
        dto.setQtype(question.getQtype());
        dto.setOptions(question.getOptionList());
        dto.setId(question.getId());
        dto.setSort(question.getSort());
        dto.setTitle(question.getTitle());
        return dto;
    }
}
