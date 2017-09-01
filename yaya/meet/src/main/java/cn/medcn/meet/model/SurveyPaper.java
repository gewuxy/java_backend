package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_survey_paper")
public class SurveyPaper implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String paperName;

    private Date createTime;

    private String category;

    private Integer owner;

    @Transient
    private List<SurveyQuestion> questionList;
}
