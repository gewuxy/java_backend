package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_exam_history_item")
public class ExamHistoryItem implements Serializable {

    @Id
    private Integer id;
    /**所属的答题卡ID*/
    private Integer historyId;
    /**对应的试题ID*/
    private Integer questionId;
    /**正确答案*/
    private String rightKey;
    /**用户提交的答案*/
    private String answer;
    /**该题分数*/
    private Integer point;
    /**得分*/
    private Integer score;
}
