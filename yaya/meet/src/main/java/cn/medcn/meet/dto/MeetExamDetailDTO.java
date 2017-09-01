package cn.medcn.meet.dto;

import cn.medcn.meet.model.ExamQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**考试明细
 * Created by LiuLP on 2017/6/12/012.
 */
@Data
@NoArgsConstructor
public class MeetExamDetailDTO {

    private Integer userId;

    private String nickname;

    private String headimg;

    //医生职称
    private String title;

    //得分
    private Integer score;

    private Integer paperId;

}
