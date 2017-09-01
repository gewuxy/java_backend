package cn.medcn.meet.dto;

import cn.medcn.meet.model.SurveyPaper;
import cn.medcn.meet.model.SurveyQuestion;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
public class SurveyPaperDTO implements Serializable{
    private Integer id;

    private String paperName;

    private List<SurveyQuestionDTO> questions;

    public static SurveyPaperDTO build(SurveyPaper paper){
        SurveyPaperDTO dto = new SurveyPaperDTO();
        dto.setId(paper.getId());
        dto.setPaperName(paper.getPaperName());
        if(paper.getQuestionList() != null){
            List<SurveyQuestionDTO> questions = Lists.newArrayList();
            for(SurveyQuestion question:paper.getQuestionList()){
                questions.add(SurveyQuestionDTO.build(question));
            }
            dto.setQuestions(questions);
        }
        return dto;
    }
}
