package cn.medcn.meet.dto;

import cn.medcn.meet.model.ExamQuestion;
import cn.medcn.meet.model.SurveyHistoryItem;
import cn.medcn.meet.model.SurveyQuestion;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**考试明细
 * Created by LiuLP on 2017/6/12/012.
 */
@Data
@NoArgsConstructor
public class MeetSurveyDetailDTO {

    private Integer userId;

    private Integer paperId;

    private String nickname;

    private String headimg;

    //医生职称
    private String title;



}
