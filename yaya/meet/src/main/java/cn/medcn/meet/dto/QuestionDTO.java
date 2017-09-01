package cn.medcn.meet.dto;

import cn.medcn.common.utils.KeyValuePair;
import cn.medcn.meet.model.ExamQuestion;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/4/26.
 */
@Data
@NoArgsConstructor
public class QuestionDTO implements Serializable {

    private Integer id;

    private String title;

    private List<KeyValuePair> options;

    private Integer qtype;

    private String rightKey;

    private Integer point;

    private Integer sort;


    public static QuestionDTO build(ExamQuestion question){
        QuestionDTO dto = new QuestionDTO();
        if(question != null){
            dto.setQtype(question.getQtype());
            dto.setTitle(question.getTitle());
            dto.setId(question.getId());
            dto.setRightKey(question.getRightKey());
            dto.setOptions(question.getOptionList());
            dto.setPoint(question.getPoint());
        }
        return dto;
    }
}
