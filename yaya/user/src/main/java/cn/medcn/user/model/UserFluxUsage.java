package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户流量使用记录
 * Created by lixuan on 2017/11/10.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_user_flux_usage")
public class UserFluxUsage implements Serializable{

    @Id
    protected String id;

    protected String userId;

    protected Integer expense;

    protected Date expenseTime;//最后记录时间

    protected String videoDownUrl;

    protected Date expireTime;

    protected String meetingId;
}
