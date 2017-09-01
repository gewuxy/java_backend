package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_meet_survey")
public class MeetSurvey implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String meetId;

    private Integer moduleId;

    private Integer paperId;

    @Transient
    private SurveyPaper surveyPaper;
}
