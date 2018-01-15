package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2018/1/10.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_star_rate_history")
public class CspStarRateHistory implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    protected Integer id;
    //用户cookie中的票据
    protected String ticket;
    //课件ID
    protected Integer courseId;
    //评分
    protected Integer score;
    //评分时间
    protected Date rateTime;

    @Transient
    protected List<CspStarRateHistoryDetail> details;
}
