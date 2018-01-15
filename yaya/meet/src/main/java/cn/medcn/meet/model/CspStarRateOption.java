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
@Table(name = "t_csp_star_rate_option")
public class CspStarRateOption implements Serializable {

    @Id
    protected Integer id;

    protected String title;

    protected Integer courseId;
}
