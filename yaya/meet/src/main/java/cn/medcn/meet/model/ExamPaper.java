package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_exam_paper")
public class ExamPaper implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    /**试卷名*/
    private String paperName;
    /**试卷总分*/
    private Integer totalPoint;
    /**评分规则 nxm+nxm n为分数 m为题数*/
    private String scorePolicy;
    /**创建时间*/
    private Date createTime;
    /**试卷拥有者ID*/
    private Integer owner;
    /**是否共享*/
    private Boolean shared;
    /**试卷科室*/
    private String category;

    @Transient
    private List<ExamQuestion> questionList;
}
