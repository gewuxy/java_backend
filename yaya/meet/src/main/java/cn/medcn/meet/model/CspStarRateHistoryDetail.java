package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2018/1/10.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_star_rate_history_detail")
public class CspStarRateHistoryDetail implements Serializable {

    @Id
    protected Integer id;
    //课件ID
    protected Integer courseId;
    //对应的评分历史ID
    protected Integer historyId;
    //分数
    protected Integer score;
    //对应的评分项ID
    protected Integer optionId;
}
