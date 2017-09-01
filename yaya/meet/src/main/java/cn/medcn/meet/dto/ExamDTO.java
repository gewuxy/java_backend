package cn.medcn.meet.dto;

import cn.medcn.meet.model.MeetExam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/26.
 */
@Data
@NoArgsConstructor
public class ExamDTO implements Serializable {

    private Integer id;

    private Integer usetime;

    private PaperDTO paper;

    private String meetId;

    private Integer moduleId;

    private Boolean shuffle;

    private Date serverTime;

    private Date startTime;

    private Date endTime;

    private Boolean finished;

    private Integer finishTimes;

    private Integer resitTimes;

    private Integer passScore;

    private Integer score;

    private Integer timeLimitType;


    public static ExamDTO build(MeetExam exam){
        ExamDTO dto = new ExamDTO();
        if(exam != null){
            dto.setId(exam.getId());
            dto.setMeetId(exam.getMeetId());
            dto.setUsetime(exam.getUsetime());
            dto.setModuleId(exam.getModuleId());
            dto.setPaper(PaperDTO.build(exam.getExamPaper()));
            dto.setShuffle(exam.getQuestionReorder());
            dto.setStartTime(exam.getStartTime());
            dto.setTimeLimitType(exam.getTimeLimitType());
            dto.setEndTime(exam.getEndTime());
            dto.setPassScore(exam.getPassScore() == null?0:exam.getPassScore());
            dto.setResitTimes(exam.getResitTimes() == null?0:exam.getResitTimes());
        }
        dto.setServerTime(new Date());
        return dto;
    }
}
