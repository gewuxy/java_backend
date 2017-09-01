package cn.medcn.meet.dto;

import cn.medcn.meet.model.ExamPaper;
import cn.medcn.meet.model.ExamQuestion;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2017/4/26.
 */
@Data
@NoArgsConstructor
public class PaperDTO implements Serializable {

    private Integer id;

    private String name;
    /**试卷总分*/
    private Integer totalPoint;
    /**评分策略*/
    private String scorePolicy;

    private List<QuestionDTO> questions;

    public static PaperDTO build(ExamPaper examPaper){
        PaperDTO dto = new PaperDTO();
        if(examPaper != null){
            dto.setId(examPaper.getId());
            dto.setName(examPaper.getPaperName());
            dto.setTotalPoint(examPaper.getTotalPoint());
            dto.setScorePolicy(examPaper.getScorePolicy());
            List<QuestionDTO> list = Lists.newArrayList();
            int i =1 ;
            for(ExamQuestion question:examPaper.getQuestionList()){
                QuestionDTO questionDTO = QuestionDTO.build(question);
                questionDTO.setSort(i);
                list.add(questionDTO);
                i++;
            }
            dto.setQuestions(list);
        }
        return dto;
    }
}
