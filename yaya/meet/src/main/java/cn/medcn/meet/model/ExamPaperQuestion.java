package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_exam_paper_question")
public class ExamPaperQuestion implements Serializable {

    @Id
    private Integer id;
    /**试卷ID*/
    private Integer paperId;
    /**试题ID*/
    private Integer questionId;
    /**分值*/
    private Integer point;
    /**在试题中的排序*/
    private Integer sort;
}
