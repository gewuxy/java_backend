package cn.medcn.meet.dto;

import cn.medcn.meet.model.MeetSurvey;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/27.
 */
@Data
@NoArgsConstructor
public class SurveyDTO implements Serializable {
    private Integer id;

    private String meetId;

    private Integer moduleId;

    private Integer paperId;

    private SurveyPaperDTO paper;

    private Boolean finished;

    public static SurveyDTO build(MeetSurvey survey){
        SurveyDTO dto = new SurveyDTO();
        dto.setId(survey.getId());
        dto.setMeetId(survey.getMeetId());
        dto.setModuleId(survey.getModuleId());
        dto.setPaperId(survey.getPaperId());
        if(survey.getSurveyPaper() != null){
            SurveyPaperDTO paperDTO = SurveyPaperDTO.build(survey.getSurveyPaper());
            dto.setPaper(paperDTO);
        }
        return dto;
    }
}
